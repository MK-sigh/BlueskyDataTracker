import streamlit as st
import requests
import pandas as pd
from sqlalchemy import create_engine
import os
from dotenv import load_dotenv

# --- 設定 ---
SPRINGBOOT_URL = "http://localhost:8501/api/v1/collector/run?"
# DB接続情報
# .envファイルを読み込む
load_dotenv()

# 環境変数を取得する
db_url = os.getenv("DATABASE_URL")
db_user = os.getenv("DATABASE_USER")
db_pass = os.getenv("DATABASE_PASSWORD")

# 形式: postgresql://ユーザー名:パスワード@ホスト名:ポート番号/データベース名
DB_URL = db_url.replace("jdbc:", "").replace("://", f"://{db_user}:{db_pass}@")

st.set_page_config(page_title="Bluesky 分析ダッシュボード", layout="wide")
st.title("🦋 Bluesky データ分析ダッシュボード")

# --- サイドバー：データ収集命令 ---
st.sidebar.header("データ収集")
query = st.sidebar.text_input("検索キーワード", value="Python")
if st.sidebar.button("Spring Bootで収集開始"):
    with st.spinner("収集しています..."):
        try:
            # Spring BootのAPIを叩く
            response = requests.get(SPRINGBOOT_URL, params={"q": query})
            if response.status_code == 200:
                st.sidebar.success("データ取得に成功しました。")
                # st.sidebar.success(f"成功: {response.text}")
            else:
                st.sidebar.error(f"失敗: ステータスコード {response.status_code}")
        except Exception as e:
            st.sidebar.error(f"エラー: {e}")

# --- メイン画面：データ可視化 ---
st.header("📊 収集済みデータの分析")

try:
    engine = create_engine(DB_URL)

    queries = {
    "posts": "SELECT * FROM posts ORDER BY created_at DESC",
    "tag_counts": """
        SELECT tag AS ＃タグ ,COUNT(post_tags.tag_id) AS 投稿数 FROM tags
        LEFT JOIN post_tags ON post_tags.tag_id = tags.id
        GROUP BY tags.tag
        ORDER BY 投稿数 DESC;
    """
}

    # DBからデータを読み込む
    df = {name: pd.read_sql(q, engine) for name, q in queries.items()}

    if not df["posts"].empty:
        # 基本統計
        st.write(f"現在の総投稿数: {len(df["posts"])} 件")

        # 1. データのプレビュー
        st.subheader("最新の投稿データ")
        st.dataframe(df["posts"].head(10))

        # 2. 時系列グラフ（投稿数の推移）
        st.subheader("投稿数の推移")
        df["posts"]['created_at'] = pd.to_datetime(
            df["posts"]['created_at'], format='ISO8601', utc=True)
        # 型を確認するためのデバッグコード
        # print(df['created_at'].dtype)
        time_series = df["posts"].set_index('created_at').resample('H').size()
        st.line_chart(time_series)

        st.subheader("投稿タグTOP10")
        st.dataframe(df["tag_counts"].head(10))

    else:
        st.warning("DBにデータがありません。サイドバーから収集してください。")

except Exception as e:
    st.error(f"エラー: {e}")
