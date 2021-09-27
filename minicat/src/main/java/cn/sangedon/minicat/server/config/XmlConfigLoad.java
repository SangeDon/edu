package cn.sangedon.minicat.server.config;

import cn.sangedon.minicat.server.mapper.MapperHost;
import java.io.InputStream;
import java.util.List;
import lombok.Data;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * xml配置加载解析
 * @author dongliangqiong 2021-09-26 09:28
 */
@Data
public class XmlConfigLoad {
    private static final String SERVER_PATH = "server.xml";

    private static final String WEB_NAME = "web.xml";

    private String port = "8080";

    private MapperHost mapperHost = new MapperHost();

    public void parseServer() {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(SERVER_PATH);) {
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(is);
            Element rootElement = doc.getRootElement();

            List<Element> serviceNodes = rootElement.selectNodes("//Service");
            for (int i = 0; i < serviceNodes.size(); i++) {
                Element element = serviceNodes.get(i);
                Node connector = element.selectSingleNode("Connector");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void parseWeb() {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(WEB_NAME);) {
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(is);
            Element rootElement = doc.getRootElement();

            List<Element> serviceNodes = rootElement.selectNodes("//servlet");
            for (int i = 0; i < serviceNodes.size(); i++) {
                Element element = serviceNodes.get(i);
                Element servletNameElement = (Element) element.selectSingleNode("servlet-name");
                String servletName = servletNameElement.getStringValue();

                Element servletClassElement = (Element) element.selectSingleNode("servlet-class");
                String servletClass = servletClassElement.getStringValue();

                Element servletMapping = (Element) element
                    .selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();

                map.put(urlPattern, (HttpServlet) Class.forName(servletClass).newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
