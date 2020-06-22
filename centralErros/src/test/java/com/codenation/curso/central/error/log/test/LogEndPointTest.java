package com.codenation.curso.central.error.log.test;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.nio.charset.Charset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.codenation.curso.central.error.CentralErrosApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=CentralErrosApplication.class)
@AutoConfigureMockMvc
public class LogEndPointTest {
	
	String token = "BearereyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJEQVlAMTIzNC5DT00iLCJleHAiOjE1OTIzNTkzNTR9.5ksunfGR3i798nnS3a_g_C7Yg2WMYVp36cBrtiThqYpHfKJqnNeOit-EUDYI8ufQaME8yZp7rjxasK2vJhmN5Q";
	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
  	      MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
    @Autowired
    private MockMvc mvc;
    
    Gson gson = new GsonBuilder().create();
    
    @Test
    public void shouldReturnAccessDenied() throws Exception {
    	
       mvc.perform(MockMvcRequestBuilders.get("/v1/api/central-error/1")).andExpect(status().isForbidden());
   
      
    }

    @Test
    public void shouldReturn200FindById() throws Exception {
      
   
        mvc.perform(MockMvcRequestBuilders.get("/v1/api/central-error/8")
                .header("Authorization", token))
                .andExpect(status().isOk());
    }
    
    @Test
    public void shouldReturn200DeleteById() throws Exception {
      
        mvc.perform(MockMvcRequestBuilders.delete("/v1/api/central-error/8")
                .header("Authorization", token))
                .andExpect(status().isOk());
    }
    
    
    @Test
    public void shouldReturn201SaveLog() throws Exception {
    
    	String eventJosn = " {\r\n" + 
    			"    \"date\": \"2020-03-02\",\r\n" + 
    			"    \"description\": \"teste\",\r\n" + 
    			"    \"errorLevel\": \"WARNING\",\r\n" + 
    			"    \"origin\": \"teste\",\r\n" + 
    			"    \"quantity\": 2\r\n" + 
    			"  }";
    	
    	
    	MvcResult result= mvc.perform(MockMvcRequestBuilders.post("/v1/api/central-error/save")
                .header("Authorization", token) .contentType(APPLICATION_JSON_UTF8).content(eventJosn)).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(201, status);
    }
    
    @Test
    public void shouldReturn200UpdateLog() throws Exception {
    
    	String eventJosn = " {\r\n" + 
    			"    \"date\": \"2021-03-03\",\r\n" + 
    			"    \"description\": \"teste\",\r\n" + 
    			"    \"errorLevel\": \"ERROR\",\r\n" + 
    			"    \"origin\": \"teste\",\r\n" + 
    			"    \"quantity\": 2\r\n" + 
    			"  }";
    	
    	
    	MvcResult result= mvc.perform(MockMvcRequestBuilders.put("/v1/api/central-error/10")
                .header("Authorization", token) .contentType(APPLICATION_JSON_UTF8).content(eventJosn)).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(200, status);
    }
   
    @Test
    public void shouldReturn200GetLogOrderBy() throws Exception {
    	
        mvc.perform(MockMvcRequestBuilders.get("/v1/api/central-error/getAll_order_by?param=origin")
                .header("Authorization", token))
                .andExpect(status().isAccepted());
    }
    
    @Test
    public void shouldReturn200GetLogFilter() throws Exception {
    	
        mvc.perform(MockMvcRequestBuilders.get("/v1/api/central-error/getAll?origin=COMPUTADOR")
                .header("Authorization", token))
                .andExpect(status().isAccepted());
    }

    
}
