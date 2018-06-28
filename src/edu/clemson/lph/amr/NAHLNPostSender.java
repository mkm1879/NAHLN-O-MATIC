/**
 * Copyright Jan 24, 2018 Michael K Martin
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.clemson.lph.amr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import edu.clemson.lph.amr.exceptions.ConfigException;
import edu.clemson.lph.amr.exceptions.HL7Exception;

/**
 * 
 */
public class NAHLNPostSender implements Sender {
	public static final Logger logger = Logger.getLogger(NahlnOMaticAMR.class.getName());
	
	public NAHLNPostSender() {
		
	}
	
	@Override
	public String send( String sMsg ) throws HL7Exception, ConfigException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		String sURI = ConfigFile.getHost();
		HttpPost post = new HttpPost(sURI);
		post.addHeader("username", ConfigFile.getUser());
		post.addHeader("password", ConfigFile.getPassword());
		post.setEntity(new StringEntity(sMsg, ContentType.TEXT_XML));
		HttpResponse response = null;
		StringBuffer result = null;
		response = client.execute(post);
		logger.info("Response Code from " + sURI + " : " 
				+ response.getStatusLine().getStatusCode());
		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
		result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}
}
