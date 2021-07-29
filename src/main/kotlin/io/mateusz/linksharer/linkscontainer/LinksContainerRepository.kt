package io.mateusz.linksharer.linkscontainer

import org.springframework.data.jpa.repository.JpaRepository

interface LinksContainerRepository: JpaRepository<LinksContainer, Long> {
}