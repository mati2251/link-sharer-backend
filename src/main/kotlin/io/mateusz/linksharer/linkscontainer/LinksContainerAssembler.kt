package io.mateusz.linksharer.linkscontainer

import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component

@Component
class LinksContainerAssembler : RepresentationModelAssembler<LinksContainer, EntityModel<LinksContainer>> {
    override fun toModel(linksContainer: LinksContainer): EntityModel<LinksContainer> {
        return EntityModel.of(
            linksContainer,
            linkTo<LinksContainerController> {getLinksContainers()}.withRel("all")
        )
    }

}