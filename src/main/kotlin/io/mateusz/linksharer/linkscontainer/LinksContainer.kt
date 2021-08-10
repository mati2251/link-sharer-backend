package io.mateusz.linksharer.linkscontainer

import io.mateusz.linksharer.link.Link
import io.mateusz.linksharer.link.LinkAssembler
import org.springframework.hateoas.EntityModel
import java.util.stream.Collectors
import java.util.stream.Stream
import javax.persistence.*

@Entity
@Table(name = "linksContainers")
class LinksContainer() {
    @Id
    @SequenceGenerator(
        name = "links_container_sequence",
        sequenceName = "links_container_sequence",
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "links_container_sequence")
    var id: Long = 0
    var title: String = ""
    var description: String = ""

    @OneToMany(mappedBy = "linksContainer", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    private var links: Set<Link>? = null

    constructor(title: String, description: String, links: Set<Link>) : this() {
        this.title = title
        this.description = description
        this.links = links
    }

    constructor(title: String, description: String) : this() {
        this.title = title
        this.description = description
    }

    fun getLinks(): List<EntityModel<Link>>? {
        val assembler = LinkAssembler()
        return this.links?.stream()?.map(assembler::toModel)?.collect(Collectors.toList())
    }

    fun setLinks(links: Set<Link>?){
        this.links = links
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LinksContainer

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + links.hashCode()
        return result
    }

    override fun toString(): String {
        return "LinksContainer(id=$id, title='$title', description='$description')"
    }


}