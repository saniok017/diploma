package org.arhor.diploma.data.persist.domain.core

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
class CompositeId<A, B>(
    var firstId: A? = null,
    var secondId: B? = null
) : Serializable {

    private operator fun CompositeId<A, B>?.component1(): A? = this?.firstId

    private operator fun CompositeId<A, B>?.component2(): B? = this?.secondId

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CompositeId<*, *>) return false

        if (firstId != other.firstId) return false
        if (secondId != other.secondId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstId?.hashCode() ?: 0
        result = 31 * result + (secondId?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "CompositeId(firstId=$firstId, secondId=$secondId)"
    }
}



