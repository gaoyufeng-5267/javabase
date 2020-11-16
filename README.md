# java-base

## mamo

```
submodule含めて初期化
git clone --recursive ssh://xx

submoduleを初期化
git submodule update -i

submoduleの初期化が失敗する場合は一旦削除
git submodule deinit java-base
git rm java-base
git submodule add ../java-base.git
```
