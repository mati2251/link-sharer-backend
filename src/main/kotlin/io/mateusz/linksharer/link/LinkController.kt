package io.mateusz.linksharer.link

import io.mateusz.linksharer.Endpoints
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = [Endpoints.LINK_CONTROLLER_PATH])
class LinkController {

    @Autowired
    private lateinit var linkService: LinkService

    @Autowired
    private lateinit var linkAssembler: LinkAssembler

    @GetMapping
    fun getLinks(): CollectionModel<EntityModel<Link>> {
        val links = linkService.getLinks()
        return linkAssembler.toCollection(links)
    }

    @GetMapping("/{id}")
    fun getLinksById(@PathVariable id: Long): EntityModel<Link> {
        return linkAssembler.toModel(linkService.getLinkById(id))
    }
}