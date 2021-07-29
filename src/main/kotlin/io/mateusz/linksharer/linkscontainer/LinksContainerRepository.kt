package io.mateusz.linksharer.linkscontainer

import org.springframework.data.repository.CrudRepository

interface LinksContainerRepository: CrudRepository<LinksContainer, Long> {
}