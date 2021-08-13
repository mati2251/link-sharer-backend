package io.mateusz.linksharer.link

import io.mateusz.linksharer.Endpoints
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.web.bind.annotation.*

/**
 * The LinkController class is rest controller to handling all operation associated with Links
 */

@RestController
@RequestMapping(path = [Endpoints.LINK_CONTROLLER_PATH])
class LinkController {

    @Autowired
    private lateinit var linkService: LinkService

    @Autowired
    private lateinit var linkAssembler: LinkAssembler

    /**
     * getLinks method gets all methods and return as CollectionModel.
     * @return CollectionModel<EntityModel<Link>>>
     */
    @GetMapping
    fun getLinks(): CollectionModel<EntityModel<Link>> {
        val links = linkService.getLinks()
        return linkAssembler.toCollection(links)
    }

    /**
     * getLinkById method gets link by id and return as EntityModel.
     * @param id the id of link we want to get
     * @return EntityModel<Link>>
     */
    @GetMapping("/{id}")
    fun getLinkById(@PathVariable id: Long): EntityModel<Link> {
        return linkAssembler.toModel(linkService.getLinkById(id))
    }
}