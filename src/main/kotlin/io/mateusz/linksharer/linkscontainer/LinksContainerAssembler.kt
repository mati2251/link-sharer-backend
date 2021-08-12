package io.mateusz.linksharer.linkscontainer

import io.mateusz.linksharer.link.Link
import io.mateusz.linksharer.link.LinkController
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component

@Component
class LinksContainerAssembler : RepresentationModelAssembler<LinksContainer, EntityModel<LinksContainer>> {
    override fun toModel(linksContainer: LinksContainer): EntityModel<LinksContainer> {
        return EntityModel.of(
            linksContainer,
            linkTo<LinksContainerController> {getLinksContainers()}.withRel("all"),
            linkTo<LinksContainerController> {getLinks(linksContainer.id)}.withRel("links"),
            linkTo<LinksContainerController> {getLinksContainer(linksContainer.id)}.withSelfRel()
        )
    }

    fun toModel(links: List<EntityModel<Link>>, id: Long): CollectionModel<EntityModel<Link>> {
        return CollectionModel.of(
            links,
            linkTo<LinksContainerController> {getLinksContainers()}.withRel("containers"),
            linkTo<LinksContainerController> {getLinksContainer(id)}.withRel("container"),
            linkTo<LinkController> {getLinks()}.withRel("all"),
            linkTo<LinksContainerController> {getLinks(id)}.withSelfRel()
        )
    }

}