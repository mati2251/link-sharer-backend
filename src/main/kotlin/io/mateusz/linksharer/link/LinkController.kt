package io.mateusz.linksharer.link

import io.mateusz.linksharer.linkscontainer.LinksContainerNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
@RequestMapping(path = ["api/v1/link"])
class LinkController {

    @Autowired
    private lateinit var linkService: LinkService

    @Autowired
    private lateinit var linkAssembler: LinkAssembler

    @GetMapping
    fun getLinks(): CollectionModel<EntityModel<Link>> {
        val links = linkService.getLinks().stream().map(linkAssembler::toModel).collect(Collectors.toList())
        return CollectionModel.of(links, linkTo<LinkController> { getLinks() }.withSelfRel())
    }

    @GetMapping("/{id}")
    fun getLinksById(@PathVariable id: Long): EntityModel<Link> {
        return linkAssembler.toModel(linkService.getLinkById(id))
    }

    @GetMapping("container/{id}")
    fun getLinksByContainerId(@PathVariable id: Long): CollectionModel<EntityModel<Link>> {
        val links =
            linkService.getLinksByContainerId(id).stream().map(linkAssembler::toModel).collect(Collectors.toList())
        return CollectionModel.of(links, linkTo<LinkController> { getLinks() }.withSelfRel())
    }

    @PostMapping("container/{id}")
    fun createLink(@RequestBody newLink: Link, @PathVariable id: Long): ResponseEntity<EntityModel<Link>> {
        try {
            val entityModel = linkAssembler.toModel(linkService.createLink(newLink, id))
            return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel)
        } catch (ex: LinksContainerNotFoundException) {
            throw ex;
        }
    }

}