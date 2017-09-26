package it.infn.ct.indigo.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import org.osgi.service.component.annotations.Component;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.portlet.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import it.infn.ct.indigo.portlet.Parameter;
import java.util.Random;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.PrintWriter;

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=INFN",
		"com.liferay.portlet.header-portlet-javascript=/js/fg-api.js",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Galaxy Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
//@Controller("galaxyPortlet")
//@RequestMapping(value = "VIEW")
public class GalaxyPortlet extends MVCPortlet {
    final private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss,SSS");

    private String logEvent(String text) {
        String log = sdf.format(new Date()) + " " + text;
        System.out.println(log);
        return log;
    }

    @Override
    public void serveResource(ResourceRequest resourceRequest,
            ResourceResponse resourceResponse) throws IOException,
    PortletException {
        try {
            String ans = ParamUtil.getString(resourceRequest, "json");
            JsonObject jsonObject = new Gson().fromJson(ans, JsonObject.class);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String out = gson.toJson(jsonObject);

            createParamFile(out);
        }
        catch(Exception e) {
            e.printStackTrace(System.out);
        }
        super.serveResource(resourceRequest, resourceResponse);
    }
    
    public void createParamFile(String json) {
        File file = new File("/home/futuregateway/FutureGateway/fgAPIServer/apps/toscaGalaxyTest/parameters.json");
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file);
            printWriter.print(json);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    private String readFile(String path) {
        String content = null;
        try {
            if(new File(path).isFile()) {
                content = new String(Files.readAllBytes(Paths.get(path)));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
    private String readJsonFile(String pathToFile) {
        String json = readFile(pathToFile);
        if(json == null) {
            json = "{\n  \"version_of_portlet_description\": 0.2,\n  \"tabs\": [\"Virtual Hardware\", \"Galaxy Configuration\", \"Galaxy Advanced Configuration\",\"Galaxy Tools\"],\n  \"parameters\": [\n    {\n      \"display\": \"Virtual CPUs Number\",\n      \"name\": \"number_cpus\",\n      \"type\": \"list\",\n      \"value\": [\n        1,\n        2,\n        4,\n        8,\n        16,\n        32,\n        64\n      ],\n      \"tab\": 0\n    },\n    {\n      \"display\": \"Memory size (RAM)\",\n      \"name\": \"memory_size\",\n      \"type\": \"list\",\n      \"value\": [\n        \"1 GB\",\n        \"2 GB\",\n        \"4 GB\",\n        \"8 GB\",\n        \"16 GB\",\n        \"32 GB\"\n      ],\n      \"tab\": 0\n    },\n    {\n      \"display\": \"Volume storage size\",\n      \"name\": \"volume_storage\",\n      \"type\": \"list\",\n      \"value\": [\n        \"100 GB\",\n        \"1 TB\"\n      ],\n      \"tab\": 0\n    },\n    {\n      \"display\": \"SSH public key\",\n      \"name\": \"instance_key_pub\",\n      \"type\": \"text\",\n      \"value\": \"Paste here your public key\",\n      \"tab\": 0\n    },\n    {\n      \"display\": \"Galaxy version\",\n      \"name\": \"version\",\n      \"type\": \"list\",\n      \"value\": [\n        \"master\"\n      ],\n      \"tab\": 1\n    },\n    {\n      \"display\": \"Instance description (Galaxy brand)\",\n      \"name\": \"instance_description\",\n      \"type\": \"text\",\n      \"value\": \"ELIXIR-ITA Galaxy test\",\n      \"tab\": 1\n    },\n    {\n      \"display\": \"Galaxy administrator username\",\n      \"name\": \"user\",\n      \"type\": \"text\",\n      \"value\": \"admin username\",\n      \"tab\": 1\n    },\n    {\n      \"display\": \"Galaxy administrator mail address\",\n      \"name\": \"admin_email\",\n      \"type\": \"text\",\n      \"value\": \"admin mail address\",\n      \"tab\": 1\n    },\n    {\n      \"display\": \"Disable anonymous access (force everyone to log in)\",\n      \"name\": \"disable_anonymous_access\",\n      \"type\": \"list\",\n      \"value\": [\n        \"Yes\",\n        \"No\"\n      ],\n      \"tab\": 2\n    },\n    {\n      \"display\": \"Galaxy flavor\",\n      \"name\": \"galaxy_flavour\",\n      \"type\": \"list\",\n      \"value\": [\n        \"no-tools\",\n        \"NGS\"\n      ],\n      \"tab\": 3\n    }\n  ]\n}";
        }
        return json;
    }

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        renderRequest.setAttribute("json-array", readJsonFile("/home/futuregateway/FutureGateway/fgAPIServer/apps/toscaGalaxyTest/galaxy-template.json"));
        super.doView(renderRequest, renderResponse);
    }
}
