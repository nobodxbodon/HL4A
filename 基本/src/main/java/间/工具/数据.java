package 间.工具;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import 间.收集.集合;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import 间.收集.哈希表;

public class 数据 {

    private static DocumentBuilder 解析;

    static {
        try {
            DocumentBuilderFactory $工厂 = DocumentBuilderFactory.newInstance();
            解析 = $工厂.newDocumentBuilder();
        } catch (Exception $错误) {}
    }

    public static 集合 解析XML(String $XML) throws Exception {
        集合 $返回 = new 集合();
        Document $文档 = 解析.parse(流.输入.字节($XML.getBytes()));
        NodeList $所有 =  $文档.getDocumentElement().getChildNodes();
        int $数量 = $所有.getLength();
        for (int $键值 = 0;$键值 < $数量;$键值 ++) {
            Node $节点 = $所有.item($键值);
            解析节点($返回,$节点);
        }
        return $返回;
    }
    
    private static void 解析节点(集合 $返回,Node $节点) {
        集合 $集合 = new 集合();
        $集合.添加($节点.getNodeName());
    }

}
