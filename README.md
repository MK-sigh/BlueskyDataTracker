# Bluesky Data Tracker

Blueskyから特定の技術系ハッシュタグ（現在は `#エンジニア`）を含む投稿を定期的に取得し、データベースに保存するSpring Bootアプリケーションです。

## 📋 プロジェクト概要

このシステムは、Bluesky API（AT Protocol）を利用して、検索したタグに該当する投稿データを蓄積します。単なるテキストの保存だけでなく、ユーザー情報、投稿統計（いいね数、リポスト数など）、ハッシュタグをリレーショナルデータベースに正規化して保存します。


### 主な機能
- **自動定期実行**: `Spring Scheduling` により、5分間隔で自動的にAPIをポーリングします。
- **ページネーション管理**: APIの `cursor` を保持し、次回の実行時に未取得の投稿から取得を再開します。
- **データ正規化**: ユーザー、投稿、タグを分離して保存し、ハッシュタグは中間テーブルで管理します。
- **重複チェック**: 投稿固有の `URI` を利用して、DBへの二重登録を防止します。

## 🏗 技術スタック
- **Java**: 17+
- **Framework**: Spring Boot 3.3.x (Spring Data JPA)
- **HTTP Client**: Spring RestClient
- **JSON Library**: Jackson
- **Database**: PostgreSQL (推奨)

## 🗄 データベース構造

以下の4つのテーブルでデータを管理しています。

1.  **users**: 投稿者のプロフィール情報（DID、ハンドル名、表示名）
2.  **posts**: 投稿本文、投稿日時、統計情報（いいね・リポスト等）、言語設定
3.  **tags**: ハッシュタグのマスター（一意のタグ名）
4.  **post_tags**: 投稿とタグを紐付ける中間テーブル（多対多の関係を解消）

## 🚀 セットアップと実行

### 1. データベースの準備
SQLなどで以下の構造を持つデータベースを作成してください。JPAの `hibernate.ddl-auto` を設定している場合は、エンティティから自動生成も可能です。
```
create table public.users (
  id serial not null
  , did character varying(255) not null
  , handle character varying(255) not null
  , display_name character varying(255)
  , created_account_at character varying(255)
  , primary key (id)
);
```
```
create table public.posts (
  id serial not null
  , text text not null
  , created_at character varying(255) not null
  , author_id integer
  , language character varying(255) not null
  , cid character varying(255) default 0 not null
  , indexed_at character varying(255) not null
  , like_count integer not null
  , reply_count integer not null
  , repost_count integer not null
  , uri character varying(255)
  , bookmark_count integer not null
  , quote_count integer not null
  , primary key (id)
);
```
```
create table public.tags (
  id serial not null
  , tag character varying(255) not null
  , primary key (id)
);
```
```
create table public.post_tags (
  post_id integer not null
  , tag_id integer not null
  , primary key (post_id, tag_id)
);
```


### 2. 設定の変更
`src/main/resources/application-local.properties` にデータベース接続情報を記述します。

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. ビルドと実行
```
./mvnw spring-boot:run
```

## 🛠 コンポーネント解説
クラス名|役割
--|--
|BlueskyApiClient|Bluesky API (searchposts) へのHTTPリクエストを担当。
BlueskyDataProcessor|JSONのパース、ユーザー・投稿・タグのDB保存ロジックの実行。
FeedScheduler|5分ごとの定期実行管理と、ページネーション用カーソルの保持。
PostTagPK|中間テーブル post_tags の複合主キー（PostID × TagID）の定義。
User/Post/Tag/PostTag|各テーブルに対応するJPAエンティティ。

## 今後の目標
- ハッシュタグの 共起ネットワーク（Co-occurrence Network） を構築
- 「どのタグが、どのタグと一緒に使われているか」を定量化
- 時系列で比較することで、コミュニティ文脈の変化を捉える