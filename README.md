# java-base

## mamo

submodule含めて初期化
git clone --recursive ssh://xx

submoduleを初期化
git submodule update -i

submoduleの初期化が失敗する場合は一旦削除
git submodule deinit java-infra
git rm java-infra
git submodule add ../java-infra.git