package io.mateusz.linksharer.link

import io.mateusz.linksharer.linkscontainer.LinksContainerController
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component

@Component
class LinkAssembler : RepresentationModelAssembler<Link, EntityModel<Link>> {
    override fun toModel(link: Link): EntityModel<Link> {
        return EntityModel.of(
            link,
            linkTo<LinkController> { getLinksById(link.id) }.withSelfRel(),
            linkTo<LinkController> { getLinks() }.withRel("all"),
//            linkTo<LinksContainerController> { getLinksContainer(link.getContainerId()) }.withRel("container")
        )
    }

}