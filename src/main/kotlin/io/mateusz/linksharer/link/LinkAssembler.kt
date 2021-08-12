package io.mateusz.linksharer.link

import io.mateusz.linksharer.linkscontainer.LinksContainerController
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class LinkAssembler : RepresentationModelAssembler<Link, EntityModel<Link>> {
    override fun toModel(link: Link): EntityModel<Link> {
        return EntityModel.of(
            link,
            linkTo<LinkController> { getLinks() }.withRel("all"),
            linkTo<LinksContainerController> { getLinksContainer(link.getContainerId()) }.withRel("container"),
            linkTo<LinksContainerController> { getLinks(link.getContainerId()) }.withRel("containerLinks"),
            linkTo<LinkController> { getLinksById(link.id) }.withSelfRel(),
            )
    }

    fun toCollection(links: List<Link>): CollectionModel<EntityModel<Link>> {
        val linksList = links.stream().map(this::toModel).collect(Collectors.toList())
        return CollectionModel.of(linksList, linkTo<LinkController> { getLinks() }.withSelfRel())
    }

}