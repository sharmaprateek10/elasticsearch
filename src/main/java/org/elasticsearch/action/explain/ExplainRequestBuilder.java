/*
 * Licensed to ElasticSearch and Shay Banon under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. ElasticSearch licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.action.explain;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.support.BaseRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.Map;

/**
 * A builder for {@link ExplainRequest}.
 */
public class ExplainRequestBuilder extends BaseRequestBuilder<ExplainRequest, ExplainResponse> {

    private ExplainSourceBuilder sourceBuilder;

    ExplainRequestBuilder(Client client) {
        super(client, new ExplainRequest());
    }

    public ExplainRequestBuilder(Client client, String index, String type, String id) {
        super(client, new ExplainRequest());
        request().index(index);
        request().type(type);
        request().id(id);
    }

    /**
     * Sets the index to get a score explanation for.
     */
    public ExplainRequestBuilder setIndex(String index) {
        request().index(index);
        return this;
    }

    /**
     * Sets the type to get a score explanation for.
     */
    public ExplainRequestBuilder setType(String type) {
        request().type(type);
        return this;
    }

    /**
     * Sets the id to get a score explanation for.
     */
    public ExplainRequestBuilder setId(String id) {
        request().id(id);
        return this;
    }

    /**
     * Sets the routing for sharding.
     */
    public ExplainRequestBuilder setRouting(String routing) {
        request().routing(routing);
        return this;
    }

    /**
     * Simple sets the routing. Since the parent is only used to get to the right shard.
     */
    public ExplainRequestBuilder setParent(String parent) {
        request().parent(parent);
        return this;
    }

    /**
     * Sets the shard preference.
     */
    public ExplainRequestBuilder setPreference(String preference) {
        request().preference(preference);
        return this;
    }

    /**
     * Sets the query to get a score explanation for.
     */
    public ExplainRequestBuilder setQuery(QueryBuilder queryBuilder) {
        sourceBuilder().query(queryBuilder);
        return this;
    }

    public ExplainRequestBuilder setSource(BytesReference querySource, boolean unsafe) {
        request().source(querySource, unsafe);
        return this;
    }

    /**
     * Sets whether the actual explain action should occur in a different thread if executed locally.
     */
    public ExplainRequestBuilder operationThreaded(boolean threadedOperation) {
        request().operationThreaded(threadedOperation);
        return this;
    }

    protected void doExecute(ActionListener<ExplainResponse> listener) {
        if (sourceBuilder != null) {
            request.source(sourceBuilder);
        }

        client.explain(request, listener);
    }

    private ExplainSourceBuilder sourceBuilder() {
        if (sourceBuilder == null) {
            sourceBuilder = new ExplainSourceBuilder();
        }
        return sourceBuilder;
    }

}