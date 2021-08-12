package io.mateusz.linksharer.linkscontainer

import io.mateusz.linksharer.Endpoints
import io.mateusz.linksharer.link.Link
import io.mateusz.linksharer.link.LinkAssembler
import io.mateusz.linksharer.link.LinkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = [Endpoints.LINKS_CONTAINER_CONTROLLER_PATH])
class LinksContainerController {

    @Autowired
    private lateinit var service: LinksContainerService

    @Autowired
    private lateinit var assembler: LinksContainerAssembler

    @Autowired
    private lateinit var linkAssembler: LinkAssembler

    @Autowired
    private lateinit var linkService: LinkService

    @GetMapping
    fun getLinksContainers(): CollectionModel<EntityModel<LinksContainer>> {
        val containers = this.service.getLinksContainers()
        return assembler.toCollection(containers)
    }

    @GetMapping("/{id}")
    fun getLinksContainer(@PathVariable id: Long): EntityModel<LinksContainer> {
        val container = this.service.getLinksContainer(id)
        return this.assembler.toModel(container)
    }

    @PostMapping
    fun createLinksContainer(@RequestBody linksContainer: LinksContainer): ResponseEntity<EntityModel<LinksContainer>> {
        return assembler.toResponseEntity(service.createLinksContainer(linksContainer))
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

    @PostMapping("/{id}/link")
    fun getLinks(@PathVariable id: Long, @RequestBody link: Link): ResponseEntity<EntityModel<Link>> {
        val newLink = linkService.createLink(link, id)
        return linkAssembler.toResponseEntity(newLink)
    }
}