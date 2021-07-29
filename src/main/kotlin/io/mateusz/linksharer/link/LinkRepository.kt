package io.mateusz.linksharer.link

import io.mateusz.linksharer.linkscontainer.LinksContainer
import org.springframework.data.jpa.repository.JpaRepository

interface LinkRepository : JpaRepository<Link, Long> {

    fun findByLinksContainer(linksContainer: LinksContainer): List<Link>

}
