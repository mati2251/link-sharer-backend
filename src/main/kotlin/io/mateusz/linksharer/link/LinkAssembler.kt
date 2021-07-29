package io.mateusz.linksharer.link

import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler

class LinkAssembler : RepresentationModelAssembler<Link, EntityModel<Link>>{
    override fun toModel(link: Link): EntityModel<Link> {
        return EntityModel.of(
            link,
        )
    }

}