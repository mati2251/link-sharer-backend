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

    /**
     * getLinksContainer gets all containers
     * @return CollectionModel<EntityModel<LinksContainer>>
     */
    @GetMapping
    fun getLinksContainers(): CollectionModel<EntityModel<LinksContainer>> {
        val containers = this.service.getLinksContainers()
        return assembler.toCollection(containers)
    }

    /**
     * getLinkContainer gets one container
     * @param id the id of container we want to get
     * @return EntityModel<LinksContainer>
     */
    @GetMapping("/{id}")
    fun getLinkContainer(@PathVariable id: Long): EntityModel<LinksContainer> {
        val container = this.service.getLinksContainer(id)
        return this.assembler.toModel(container)
    }

    /**
     * createLinksContainer create new links container
     * @param linksContainer this is new LinksContainer which we want to create
     * @return ResponseEntity<EntityModel<LinksContainer>>
     */
    @PostMapping
    fun createLinksContainer(@RequestBody linksContainer: LinksContainer): ResponseEntity<EntityModel<LinksContainer>> {
        return assembler.toResponseEntity(service.createLinksContainer(linksContainer))
    }

    /**
     * getLinks get all links in specific container
     * @param id the id of container we want to get 
     * @return CollectionModel<EntityModel<Link>>
     */
    @GetMapping("/{id}/${Endpoints.ADD_LINK_PATH}")
    fun getLinks(@PathVariable id: Long): CollectionModel<EntityModel<Link>> {
        val container: LinksContainer = this.service.getLinksContainer(id)
        return assembler.toModel(container.getLinks(), id)
    }

    /**
     * getLinks get one specific link in specific container
     * @param id the id of container
     * @param linkId the id of link in container, that not is id in database but is id in order in container array
     * @return EntityModel<Link>
     */
    @GetMapping("/{id}/${Endpoints.ADD_LINK_PATH}/{linkId}")
    fun getLink(@PathVariable id: Long, @PathVariable linkId: Int): EntityModel<Link> {
        val container: LinksContainer = this.service.getLinksContainer(id)
        return linkAssembler.toModel(container.getLink(linkId))
    }

    /**
     * createLink create new link in specific container
     * @param id the id of container
     * @param link this is new link which we want to create
     * @return ResponseEntity<EntityModel<Link>>
     */
    @PostMapping("/{id}/${Endpoints.ADD_LINK_PATH}")
    fun createLink(@PathVariable id: Long, @RequestBody link: Link): ResponseEntity<EntityModel<Link>> {
        val newLink = linkService.createLink(link, id)
        return linkAssembler.toResponseEntity(newLink)
    }
}