package io.mateusz.linksharer.link

import io.mateusz.linksharer.linkscontainer.LinksContainer
import org.springframework.data.repository.CrudRepository

interface LinkRepository: CrudRepository<Link, Long > {

    fun findByLinksContainer(linksContainer: LinksContainer): List<Link>

}
