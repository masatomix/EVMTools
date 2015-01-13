/******************************************************************************
 * Copyright (c) 2010 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/
//�쐬��: 2013/06/12

package nu.mine.kino.projects.utils;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class HttpUtils {

    // URL�ɃA�N�Z�X�����X�|���X�̕�������擾����B
    public static String getWebPage(String url) {
        // Restlet�o�[�W����
        // ClientResource resource = new ClientResource(url);
        // String responseHtml = resource.get().getText();
        // return responseHtml;

        // jersey�o�[�W����
        Client c = Client.create();
        WebResource r = c.resource(url);
        ClientResponse getResponse = r.get(ClientResponse.class);
        String responseStr = getResponse.getEntity(String.class);
        return responseStr;

    }

    public static ClientResponse putWebPage(String url, Object obj,
            String mediaType) {
        Client c = Client.create();
        WebResource r = c.resource(url);
        ClientResponse response = r.accept(mediaType).put(ClientResponse.class,
                obj);
        return response;
    }

    public static ClientResponse putWebPage(String url, Object obj) {
        return putWebPage(url, obj, MediaType.APPLICATION_XML);
    }

}
