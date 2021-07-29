package io.mateusz.linksharer.link

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/v1/link"])
class LinkController {

    @Autowired
    private lateinit var linkService: LinkService

    @GetMapping
    fun getLinks(): MutableList<Link> {
        return linkService.getLinks()
    }

    @GetMapping("/{id}")
    fun getLinksById(@PathVariable id: Long): Link {
        return linkService.getLinkById(id);
    }

    @GetMapping("container/{id}")
    fun getLinksByContainerId(@PathVariable id: Long): List<Link> {
        return linkService.getLinksByContainerId(id);
    }
}