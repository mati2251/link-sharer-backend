package io.mateusz.linksharer.link

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
        val links = linkService.getLinksByContainerId(id).stream().map(linkAssembler::toModel).collect(Collectors.toList())
        return CollectionModel.of(links, linkTo<LinkController> { getLinks() }.withSelfRel())
    }
}