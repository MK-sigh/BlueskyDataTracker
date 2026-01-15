import streamlit as st
import requests
import pandas as pd
import os
from dotenv import load_dotenv

# --- 設定 ---
SPRINGBOOT_POST = "http://localhost:8080/api/v1/collector/run"
SPRINGBOOT_GET = "http://localhost:8080/api/v1/collector/search"
# DB接続情報
# .envファイルを読み込む
load_dotenv()

# 環境変数を取得する
db_url = os.getenv("DATABASE_URL")
db_user = os.getenv("DATABASE_USER")
db_pass = os.getenv("DATABASE_PASSWORD")

# 形式: postgresql://ユーザー名:パスワード@ホスト名:ポート番号/データベース名
DB_URL = db_url.replace("jdbc:", "").replace("://", f"://{db_user}:{db_pass}@")

st.set_page_config(page_title="Bluesky Data Tracker", layout="wide")
st.title("🦋 Bluesky  Data Tracker")

# --- サイドバー：データ収集命令 ---
st.sidebar.header("データ収集")
query = st.sidebar.text_input("検索キーワード", value="Python")
if st.sidebar.button("Spring Bootで収集開始"):
    with st.spinner("収集しています..."):
        try:
            # 1. 収集実行
            response = requests.post(SPRINGBOOT_POST, params={"q": query})
            # 2. Javaの検索APIから「フィルタリング済みデータ」を取得
            search_res = requests.get(SPRINGBOOT_GET, params={"q": query})
            # st.write("GETレスポンス:", search_res.text)
            if search_res.status_code == 200:
                # JSONを直接DataFrameに変換！
                st.session_state.posts_df = pd.DataFrame(search_res.json())
                st.sidebar.success(f"{len(st.session_state.posts_df)}件のデータを取得しました")
            else:
                st.sidebar.error("取得失敗")
        except Exception as e:
            st.sidebar.error(f"エラー: {e}")



# --- メイン画面：データ可視化 ---
st.header("📊 データ分析")
# セッション状態の初期化
if "posts_df" not in st.session_state:
    st.session_state.posts_df = pd.DataFrame()

raw_posts = st.session_state.posts_df

if not raw_posts.empty:

    # 階層構造（author.handle等）を平らな列に変換する
    # これにより 'author_handle' や 'author_displayName' という列が生まれます
    df_posts = pd.json_normalize(raw_posts.to_dict('records'), sep='_')
    
    # 列名の確認（デバッグ用：後で消してOK）
    # st.write("デバッグ列名:", df_posts.columns.tolist())
    # 0:"id"
    # 1:"uri"
    # 2:"cid"
    # 3:"text"
    # 4:"createdAt"
    # 5:"indexedAt"
    # 6:"language"
    # 7:"bookmarkCount"
    # 8:"replyCount"
    # 9:"repostCount"
    # 10:"likeCount"
    # 11:"quoteCount"
    # 12:"authorId"
    # 13:"author_id"
    # 14:"author_did"
    # 15:"author_handle"
    # 16:"author_displayName"
    # 17:"author_createdAccountAt"

    st.write(f"現在の表示件数: {len(df_posts)} 件")
    
    # 時系列グラフの処理
    # Javaから来る時間は文字列なので変換が必要
    df_posts['createdAt'] = pd.to_datetime(
        df_posts['createdAt'], format='ISO8601', utc=True)

    st.subheader("最新の投稿データ")
    # APIから届いた全データから、必要な列だけを抽出
    display_df = df_posts[["author_handle", "author_displayName", "text", "createdAt"]]
    st.dataframe(display_df)

    st.subheader("投稿数の推移")
    time_series = df_posts.set_index('createdAt').resample('H').size()
    st.line_chart(time_series)

# --- タグの集計処理 ---
# 0:"postId"
# 1:"tagId"
# 2:"Tag.id"
# 3:"Tag.tag"

    # --- タグの集計処理 ---
    st.subheader("🏷️ 投稿タグ TOP 10")
    # df_posts が空でなく、かつ 'tags' 列が存在するかチェック
    if not df_posts.empty and 'tags' in df_posts.columns:
        try:
            # 1. 'tags' 列にはリストが入っているので、それを展開して1行ずつにする
            # これにより、1つの投稿に3つタグがあれば、3行に増えます
            df_exploded = df_posts.explode('tags')
            
            # 2. 展開された中身（辞書）をさらに表形式に展開する
            # ここで辞書の中の 'tag_tag' (Tagオブジェクト内のtag文字列) を取り出す
            tags_normalized = pd.json_normalize(df_exploded['tags'].dropna())
            
            if not tags_normalized.empty:
                # Javaの PostTag.java で private Tag Tag; としている場合、
                # カラム名は 'tag_tag' になっている可能性が高いです
                # もし動かなければ、st.write(tags_normalized.columns.tolist()) で確認
                tag_col = 'Tag.tag' 
                
                if tag_col in tags_normalized.columns:
                    tag_counts = tags_normalized[tag_col].value_counts().reset_index()
                    tag_counts.columns = ['タグ', '投稿数']
                    
                    col1, col2 = st.columns([2, 1])
                    with col1:
                        st.bar_chart(tag_counts.set_index('タグ').head(10))
                    with col2:
                        st.dataframe(tag_counts.head(10), hide_index=True)
                else:
                    st.info("タグ名が見つかりませんでした。")
            else:
                st.info("タグが付いている投稿はありません。")
                
        except Exception as e:
            st.error(f"集計エラー: {e}")
    else:
        st.info("表示できるタグ情報がありません。")