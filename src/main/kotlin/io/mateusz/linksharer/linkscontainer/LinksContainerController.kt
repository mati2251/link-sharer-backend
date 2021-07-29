package io.mateusz.linksharer.linkscontainer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
@RequestMapping(path = ["api/v1/container"])
class LinksContainerController {

    @Autowired
    private lateinit var linksContainerService: LinksContainerService

    @Autowired
    private lateinit var assembler: LinksContainerAssembler

    @GetMapping
    fun getLinksContainers(): CollectionModel<EntityModel<LinksContainer>> {
        val containers = this.linksContainerService.getLinksContainers()
            .map(assembler::toModel).stream().collect(Collectors.toList())
        return CollectionModel.of(containers, linkTo<LinksContainerController> { getLinksContainers() }.withSelfRel())
    }

    @GetMapping("/{id}")
    fun getLinksContainer(@PathVariable id: Long): EntityModel<LinksContainer> {
        val container = this.linksContainerService.getLinksContainer(id)
        return assembler.toModel(container)
    }
}