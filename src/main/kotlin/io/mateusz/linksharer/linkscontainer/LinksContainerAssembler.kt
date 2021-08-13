package io.mateusz.linksharer.linkscontainer

import io.mateusz.linksharer.link.Link
import io.mateusz.linksharer.link.LinkController
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class LinksContainerAssembler : RepresentationModelAssembler<LinksContainer, EntityModel<LinksContainer>> {
    override fun toModel(linksContainer: LinksContainer): EntityModel<LinksContainer> {
        return EntityModel.of(
            linksContainer,
            linkTo<LinksContainerController> { getLinksContainers() }.withRel("all"),
            linkTo<LinksContainerController> { getLinks(linksContainer.id) }.withRel("links"),
            linkTo<LinksContainerController> { createLink(linksContainer.id, Link("", "")) }.withRel("newLink"),
            linkTo<LinksContainerController> { getLinkContainer(linksContainer.id) }.withSelfRel()
        )
    }

    fun toModel(links: List<EntityModel<Link>>, id: Long): CollectionModel<EntityModel<Link>> {
        return CollectionModel.of(
            links,
            linkTo<LinksContainerController> { getLinksContainers() }.withRel("containers"),
            linkTo<LinksContainerController> { getLinkContainer(id) }.withRel("container"),
            linkTo<LinkController> { getLinks() }.withRel("all"),
            linkTo<LinksContainerController> { getLinks(id) }.withSelfRel()
        )
    }

    fun toResponseEntity(linksContainer: LinksContainer): ResponseEntity<EntityModel<LinksContainer>> {
        val entityModel = this.toModel(linksContainer)
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel)
    }

    fun toCollection(containersList: MutableIterable<LinksContainer>): CollectionModel<EntityModel<LinksContainer>> {
        val containers = containersList
            .map(this::toModel).stream().collect(Collectors.toList())
        return CollectionModel.of(containers, linkTo<LinksContainerController> { getLinksContainers() }.withSelfRel())
    }
}