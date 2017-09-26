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
		"javax.portlet.display-name=Galaxy Elastic Cluster Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class GalaxyElasticClusterPortlet extends MVCPortlet {
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
        File file = new File("/home/futuregateway/FutureGateway/fgAPIServer/apps/toscaGalaxyElasticClusterTest/parameters.json");
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
            json = "{\n  \"version_of_portlet_description\": 0.2,\n  \"tabs\": [\n    \"Virtual Hardware\",\n    \"Galaxy Configuration\"\n  ],\n  \"parameters\": [\n    {\n      \"tab\": 0,\n      \"value\": [\n        1,\n        2,\n        3,\n        4,\n        5,\n        6,\n        7,\n        8,\n        9,\n        10\n      ],\n      \"type\": \"list\",\n      \"name\": \"wn_num\",\n      \"display\": \"Maximum number of WNs in the elastic cluster\"\n    },\n    {\n      \"tab\": 0,\n      \"value\": [\n        1,\n        2,\n        4,\n        8,\n        16,\n        32,\n        64\n      ],\n      \"type\": \"list\",\n      \"name\": \"fe_cpus\",\n      \"display\": \"Numer of CPUs for the front-end node\"\n    },\n    {\n      \"tab\": 0,\n      \"value\": [\n        \"1 GB\",\n        \"2 GB\",\n        \"4 GB\",\n        \"8 GB\",\n        \"16 GB\",\n        \"32 GB\"\n      ],\n      \"type\": \"list\",\n      \"name\": \"fe_mem\",\n      \"display\": \"Amount of Memory for the front-end node\"\n    },\n    {\n      \"tab\": 0,\n      \"value\": [\n        1,\n        2,\n        4,\n        8,\n        16,\n        32,\n        64\n      ],\n      \"type\": \"list\",\n      \"name\": \"wn_cpus\",\n      \"display\": \"Numer of CPUs for the WNs\"\n    },\n    {\n      \"tab\": 0,\n      \"value\": [\n        \"1 GB\",\n        \"2 GB\",\n        \"4 GB\",\n        \"8 GB\",\n        \"16 GB\",\n        \"32 GB\"\n      ],\n      \"type\": \"list\",\n      \"name\": \"wn_mem\",\n      \"display\": \"Amount of Memory for the WNs\"\n    },\n    {\n      \"tab\": 1,\n      \"value\": \"admin mail address\",\n      \"type\": \"text\",\n      \"name\": \"admin_email\",\n      \"display\": \"Galaxy administrator mail address\"\n    },\n    {\n      \"tab\": 1,\n      \"value\": \"your API key\",\n      \"type\": \"text\",\n      \"name\": \"admin_api_key\",\n      \"display\": \"Key to access the API with admin role\"\n    },\n    {\n      \"tab\": 1,\n      \"value\": \"Paste here your public key\",\n      \"type\": \"text\",\n      \"name\": \"instance_key_pub\",\n      \"display\": \"SSH public key\"\n    },\n    {\n      \"tab\": 1,\n      \"value\": [\n        \"master\"\n      ],\n      \"type\": \"list\",\n      \"name\": \"version\",\n      \"display\": \"Galaxy version\"\n    },\n    {\n      \"tab\": 1,\n      \"value\": \"ELIXIR-ITA Galaxy test\",\n      \"type\": \"text\",\n      \"name\": \"instance_description\",\n      \"display\": \"Instance description (Galaxy brand)\"\n    }\n  ]\n}";
        }
        return json;
    }

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        renderRequest.setAttribute("json-array", 
            readJsonFile("/home/futuregateway/FutureGateway/fgAPIServer/apps/toscaGalaxyElasticClusterTest/galaxy-elastic-cluster-template.json"));
        super.doView(renderRequest, renderResponse);
    }
}
