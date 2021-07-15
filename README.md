# AES256-
AES256で暗号化するバッチ

1. Jarを作成（ゴールにpackageを指定してビルド）
1. key.propertiesに秘密鍵と初期化ベクトルを設定
1. 作成したJarとkey.propertiesを同じフォルダに配置
1. コマンドプロンプトを開き、Jarが配置されたフォルダに移動
1. 以下のコマンドを実行
```
java -jar ensino-0.0.1-SNAPSHOT-jar-with-dependencies.jar [暗号化する申請番号]
```
例）`java -jar ensino-0.0.1-SNAPSHOT-jar-with-dependencies.jar 1234567890`
