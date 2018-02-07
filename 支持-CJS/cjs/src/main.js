let $当前版本 = 2;
let $类库文件 = libs_inthis.getFilesDir().getParent() + "/hl4a" + $当前版本 + ".dex";

let $类库字节码 = $类库字节码 || null;

if ($类库字节码 == null) {
    throw "没有引入hfc.dex.js";
}

let _释放文件 = function() {
    let $输出流 = new java.io.FileOutputStream($类库文件);
    let $字节码 = android.util.Base64.decode($类库字节码,android.util.Base64.DEFAULT);
    $输出流.write($字节码);
    $输出流.close();
}

if (!java.io.File($类库文件).isFile()) {
    _释放文件();
}
