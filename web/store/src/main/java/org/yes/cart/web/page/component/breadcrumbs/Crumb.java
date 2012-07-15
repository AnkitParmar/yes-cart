/*
 * Copyright 2009 Igor Azarnyi, Denys Pavlov
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.yes.cart.web.page.component.breadcrumbs;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.Serializable;

/**
 * Represents a Breadcrumb navigation object.
 * <p/>
 * User: Igor Azarny iazarny@yahoo.com
 * Date: 2011-May-17
 * Time: 9:33:59
 */
public class Crumb implements Serializable {

    private final String name;

    private final PageParameters crumbLinkParameters;

    private PageParameters removeCrumbLinkParameters;

    public String getName() {
        return name;
    }

    public PageParameters getCrumbLinkParameters() {
        return crumbLinkParameters;
    }

    public PageParameters getRemoveCrumbLinkParameters() {
        return removeCrumbLinkParameters;
    }

    public void setRemoveCrumbLinkParameters(PageParameters removeCrumbLinkParameters) {
        this.removeCrumbLinkParameters = removeCrumbLinkParameters;
    }

    public Crumb(final String name, final PageParameters crumbLinkParameters) {
        this.name = name;
        this.crumbLinkParameters = crumbLinkParameters;
    }

    public Crumb(final String name,
                 final PageParameters crumbLinkParameters,
                 final PageParameters removeCrumbLinkParameters) {
        this.name = name;
        this.crumbLinkParameters = crumbLinkParameters;
        this.removeCrumbLinkParameters = removeCrumbLinkParameters;
    }

}
