package io.mateusz.linksharer

import io.mateusz.linksharer.link.Link
import io.mateusz.linksharer.linkscontainer.LinksContainer
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel

class EntityModelContainer : EntityModel<LinksContainer>()
class CollectionModelLinks : CollectionModel<EntityModel<Link>>()
class EntityModelLink : EntityModel<Link>()
