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

package org.yes.cart.remote.service.impl;

import org.springframework.security.access.AccessDeniedException;
import org.yes.cart.domain.dto.*;
import org.yes.cart.exception.UnableToCreateInstanceException;
import org.yes.cart.exception.UnmappedInterfaceException;
import org.yes.cart.remote.service.ReindexService;
import org.yes.cart.remote.service.RemoteProductSkuService;
import org.yes.cart.service.dto.DtoProductSkuService;
import org.yes.cart.service.federation.FederationFacade;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * User: Igor Azarny iazarny@yahoo.com
 * Date: 15-May-2011
 * Time: 17:22:15
 */
public class RemoteProductSkuServiceImpl
        extends AbstractRemoteService<ProductSkuDTO>
        implements RemoteProductSkuService {

    private final DtoProductSkuService dtoProductSkuService;

    private final ReindexService reindexService;

    private final FederationFacade federationFacade;

    /**
     * Construct remote service.
     *
     * @param dtoProductSkuService dto service.
     * @param reindexService product reindex service
     * @param federationFacade facade
     */
    public RemoteProductSkuServiceImpl(
            final DtoProductSkuService dtoProductSkuService,
            final ReindexService reindexService, final FederationFacade federationFacade) {
        super(dtoProductSkuService);
        this.dtoProductSkuService = dtoProductSkuService;
        this.reindexService = reindexService;
        this.federationFacade = federationFacade;
    }

    /**
     * {@inheritDoc}
     */
    public List<? extends AttrValueDTO> getEntityAttributes(final long entityPk)
            throws UnmappedInterfaceException, UnableToCreateInstanceException {
        try {
            getById(entityPk); // check access
        } catch (UnmappedInterfaceException e) {
            // ok
        } catch (UnableToCreateInstanceException e) {
            // ok
        } catch (AccessDeniedException ade) {
            return Collections.emptyList();
        }

        return dtoProductSkuService.getEntityAttributes(entityPk);
    }



    /**
     * Get all product SKUs.
     *
     * @param productId product id
     * @return list of product skus.
     */
    public List<ProductSkuDTO> getAllProductSkus(final long productId)
            throws UnmappedInterfaceException, UnableToCreateInstanceException {
        if (federationFacade.isManageable(productId, ProductDTO.class)) {
            return dtoProductSkuService.getAllProductSkus(productId);
        }
        return Collections.emptyList();
    }

    /**
     * Update attribute value.
     *
     * @param attrValueDTO value to update
     * @return updated value
     */
    public AttrValueDTO updateEntityAttributeValue(final AttrValueDTO attrValueDTO) throws UnmappedInterfaceException, UnableToCreateInstanceException {
        try {
            getById(((AttrValueProductSkuDTO) attrValueDTO).getSkuId()); // check access
        } catch (UnmappedInterfaceException e) {
            // ok
        } catch (UnableToCreateInstanceException e) {
            // ok
        }
        AttrValueProductSkuDTO rez = (AttrValueProductSkuDTO) dtoProductSkuService.updateEntityAttributeValue(attrValueDTO);
        reindexService.reindexProductSku(rez.getSkuId());
        return rez;
    }

    /**
     * Create attribute value
     *
     * @param attrValueDTO value to persist
     * @return created value
     */
    public AttrValueDTO createEntityAttributeValue(final AttrValueDTO attrValueDTO) throws UnmappedInterfaceException, UnableToCreateInstanceException {
        try {
            getById(((AttrValueProductSkuDTO) attrValueDTO).getSkuId()); // check access
        } catch (UnmappedInterfaceException e) {
            // ok
        } catch (UnableToCreateInstanceException e) {
            // ok
        }
        AttrValueProductSkuDTO rez = (AttrValueProductSkuDTO) dtoProductSkuService.createEntityAttributeValue(attrValueDTO);
        reindexService.reindexProductSku(rez.getSkuId());
        return rez;
    }

    /**
     * Delete attribute value by given pk value.
     *
     * @param attributeValuePk given pk value.
     */
    public long deleteAttributeValue(final long attributeValuePk) throws UnmappedInterfaceException, UnableToCreateInstanceException {
        final long productSkuId = dtoProductSkuService.deleteAttributeValue(attributeValuePk);
        reindexService.reindexProductSku(productSkuId);
        return productSkuId;
    }


    /**
     * Create sku price.
     *
     * @param skuPriceDTO to create in database
     * @return created sku dto
     */
    public long createSkuPrice(final SkuPriceDTO skuPriceDTO) throws UnmappedInterfaceException, UnableToCreateInstanceException {
        try {
            getById(skuPriceDTO.getProductSkuId()); // check access
        } catch (UnmappedInterfaceException e) {
            // ok
        } catch (UnableToCreateInstanceException e) {
            // ok
        }
        long rez = dtoProductSkuService.createSkuPrice(skuPriceDTO);
        reindexService.reindexProductSku(skuPriceDTO.getProductSkuId());
        return rez;

    }

    /**
     * Update sku price.
     *
     * @param skuPriceDTO to create in database
     * @return updated sku price dto
     */
    public long updateSkuPrice(final SkuPriceDTO skuPriceDTO) throws UnmappedInterfaceException, UnableToCreateInstanceException {
        try {
            getById(skuPriceDTO.getProductSkuId()); // check access
        } catch (UnmappedInterfaceException e) {
            // ok
        } catch (UnableToCreateInstanceException e) {
            // ok
        }
        long rez = dtoProductSkuService.updateSkuPrice(skuPriceDTO);
        reindexService.reindexProductSku(skuPriceDTO.getProductSkuId());
        return rez;
    }

    /** {@inheritDoc} */
    public SkuPriceDTO getSkuPrice(final long skuPriceId) {
        final SkuPriceDTO price = dtoProductSkuService.getSkuPrice(skuPriceId);
        if (price != null) {
            try {
                getById(price.getProductSkuId()); // check access
                return price;
            } catch (UnmappedInterfaceException e) {
                // ok
            } catch (UnableToCreateInstanceException e) {
                // ok
            }
        }
        return null;
    }

    /** {@inheritDoc} */
    public void removeSkuPrice(final long skuPriceId) {
        final SkuPriceDTO skuPriceDTO = dtoProductSkuService.getSkuPrice(skuPriceId);
        try {
            getById(skuPriceDTO.getProductSkuId()); // check access
        } catch (UnmappedInterfaceException e) {
            // ok
        } catch (UnableToCreateInstanceException e) {
            // ok
        }
        dtoProductSkuService.removeSkuPrice(skuPriceId);
        reindexService.reindexProductSku(skuPriceDTO.getProductSkuId());
    }

    /** {@inheritDoc} */
    public void removeAllPrices(long productId) {
        if (federationFacade.isManageable(productId, ProductDTO.class)) {
            dtoProductSkuService.removeAllPrices(productId);
            reindexService.reindexProduct(productId);
        } else {
            throw new AccessDeniedException("Access is denied");
        }
    }

    /** {@inheritDoc} */
    public void removeAllInventory(long productId) {
        if (federationFacade.isManageable(productId, ProductDTO.class)) {
            dtoProductSkuService.removeAllInventory(productId);
            reindexService.reindexProduct(productId);
        } else {
            throw new AccessDeniedException("Access is denied");
        }
    }

    /** {@inheritDoc} */
    public ProductSkuDTO getById(final long id) throws UnmappedInterfaceException, UnableToCreateInstanceException {
        final ProductSkuDTO sku = super.getById(id);
        if (sku != null) {
            if (federationFacade.isManageable(sku.getProductId(), ProductDTO.class)) {
                return sku;
            } else {
                throw new AccessDeniedException("Access is denied");
            }
        }
        return null;
    }

    /** {@inheritDoc} */
    public ProductSkuDTO getById(final long id, final Map converters) throws UnmappedInterfaceException, UnableToCreateInstanceException {
        final ProductSkuDTO sku = super.getById(id, converters);
        if (sku != null) {
            if (federationFacade.isManageable(sku.getProductId(), ProductDTO.class)) {
                return sku;
            } else {
                throw new AccessDeniedException("Access is denied");
            }
        }
        return null;
    }

    /** {@inheritDoc} */
    public ProductSkuDTO create(final ProductSkuDTO instance) throws UnmappedInterfaceException, UnableToCreateInstanceException {
        if (federationFacade.isManageable(instance.getProductId(), ProductDTO.class)) {
            ProductSkuDTO rez = super.create(instance);
            reindexService.reindexProductSku(rez.getSkuId());
            return rez;
        } else {
            throw new AccessDeniedException("Access is denied");
        }
    }

    /** {@inheritDoc} */
    public ProductSkuDTO update(final ProductSkuDTO instance) throws UnmappedInterfaceException, UnableToCreateInstanceException {
        if (federationFacade.isManageable(instance.getProductId(), ProductDTO.class)) {
            ProductSkuDTO rez = super.update(instance);
            reindexService.reindexProductSku(rez.getSkuId());
            return rez;
        } else {
            throw new AccessDeniedException("Access is denied");
        }
    }

    /** {@inheritDoc} */
    public void remove(final long id) throws UnmappedInterfaceException, UnableToCreateInstanceException {
        getById(id); // checks access
        super.remove(id);
        reindexService.reindexProductSku(id);
    }

    /**
     * {@inheritDoc}
     */
    public AttrValueDTO createAndBindAttrVal(long entityPk, String attrName, String attrValue) throws UnmappedInterfaceException, UnableToCreateInstanceException {
        throw new UnmappedInterfaceException("Not implemented");
    }
}
