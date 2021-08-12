package io.mateusz.linksharer.linkscontainer

import io.mateusz.linksharer.Endpoints
import io.mateusz.linksharer.link.Link
import io.mateusz.linksharer.link.LinkAssembler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
@RequestMapping(path = [Endpoints.LINKS_CONTAINER_CONTROLLER_PATH])
class LinksContainerController {

    @Autowired
    private lateinit var service: LinksContainerService

    @Autowired
    private lateinit var assembler: LinksContainerAssembler

    @Autowired
    private lateinit var linkAssembler: LinkAssembler

    @GetMapping
    fun getLinksContainers(): CollectionModel<EntityModel<LinksContainer>> {
        val containers = this.service.getLinksContainers()
            .map(this.assembler::toModel).stream().collect(Collectors.toList())
        return CollectionModel.of(containers, linkTo<LinksContainerController> { getLinksContainers() }.withSelfRel())
    }

    @GetMapping("/{id}")
    fun getLinksContainer(@PathVariable id: Long): EntityModel<LinksContainer> {
        val container = this.service.getLinksContainer(id)
        return this.assembler.toModel(container)
    }

    @PostMapping
    fun createLinksContainer(@RequestBody linksContainer: LinksContainer): ResponseEntity<EntityModel<LinksContainer>> {
        val entityModel = assembler.toModel(this.service.createLinksContainer(linksContainer))
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel)
    }

    @GetMapping("/{id}/link")
    fun getLinks(@PathVariable id: Long): CollectionModel<EntityModel<Link>> {
        val container: LinksContainer = this.service.getLinksContainer(id)
        return assembler.toModel(container.getLinks(), id)
    }

    @GetMapping("/{id}/link/{linkId}")
    fun getLinks(@PathVariable id: Long, @PathVariable linkId: Int): EntityModel<Link> {
        val container: LinksContainer = this.service.getLinksContainer(id)
        return linkAssembler.toModel(container.getLink(linkId))
    }
}