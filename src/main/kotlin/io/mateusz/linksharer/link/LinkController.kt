package io.mateusz.linksharer.link

import io.mateusz.linksharer.Endpoints
import io.mateusz.linksharer.linkscontainer.LinksContainerNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.NumberFormatException
import java.util.stream.Collectors

@RestController
@RequestMapping(path = [Endpoints.LINK_CONTROLLER_PATH])
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

    /*@GetMapping("/container/{id}")
    fun getLinksByContainerId(@PathVariable id: Long): CollectionModel<EntityModel<Link>> {
        val links =
            linkService.getLinksByContainerId(id).stream().map(linkAssembler::toModel).collect(Collectors.toList())
        return CollectionModel.of(links, linkTo<LinkController> { getLinks() }.withSelfRel())
    }

    @PostMapping("/container/{id}")
    fun createLink(@RequestBody newLink: Link, @PathVariable id: Long): ResponseEntity<EntityModel<Link>> {
        val entityModel = linkAssembler.toModel(linkService.createLink(newLink, id))
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel)
    }

    @PostMapping
    fun createLink(@RequestBody newLink: Link): ResponseEntity<EntityModel<Link>> {
        val entityModel = linkAssembler.toModel(linkService.createLink(newLink))
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel)
    }

    @PutMapping("/{linkId}")
    fun changeContainerById(@PathVariable linkId: Long, @RequestParam(name = "id") containerId: Long): ResponseEntity<Any> {
        try {
            val link = linkService.changeLinksContainer(linkId, containerId)
                ?: return ResponseEntity<Any>(LinksContainerNotFoundException(containerId), HttpStatus.NOT_FOUND)
            val entityModel = linkAssembler.toModel(link)
            return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel)
        } catch (ex: NumberFormatException) {
            return ResponseEntity<Any>("id param must be number", HttpStatus.BAD_REQUEST)
        }
    }*/
}