import kotlin.math.sqrt

fun main() {

    data class Point3D(val x: Int, val y: Int, val z: Int)
    data class Edge(val a: Point3D, val b: Point3D, val distance: Double)

    class UnionFind(points: List<Point3D>) {
        private val parent = mutableMapOf<Point3D, Point3D>()
        val size = mutableMapOf<Point3D, Int>()

        init {
            points.forEach {
                parent[it] = it
                size[it] = 1
            }
        }

        fun find(p: Point3D): Point3D {
            if (parent[p] != p) {
                parent[p] = find(parent[p]!!)
            }
            return parent[p]!!
        }

        fun union(p1: Point3D, p2: Point3D): Boolean {
            val root1 = find(p1)
            val root2 = find(p2)

            if (root1 == root2) return false

            parent[root2] = root1
            size[root1] = size[root1]!! + size[root2]!!
            return true
        }

        fun getComponentSizes(): List<Int> {
            val roots = mutableSetOf<Point3D>()
            parent.keys.forEach { roots.add(find(it)) }
            return roots.map { size[it]!! }
        }
    }

    fun parse(input: List<String>): List<Point3D> {
        return input.map {
            val split = it.split(",")
            Point3D(split[0].toInt(), split[1].toInt(), split[2].toInt())
        }
    }

    fun distance(a: Point3D, b: Point3D): Double {
        val dx = (a.x - b.x).toDouble()
        val dy = (a.y - b.y).toDouble()
        val dz = (a.z - b.z).toDouble()
        return sqrt(dx * dx + dy * dy + dz * dz)
    }

    fun part1(input: List<String>): Int {
        val parse = parse(input)

        val edgesList = mutableListOf<Edge>()
        for (i in parse.indices) {
            for (j in i + 1 until parse.size) {
                edgesList.add(Edge(parse[i], parse[j], distance(parse[i], parse[j])))
            }
        }
        edgesList.sortBy { it.distance }

        val uf = UnionFind(parse)
        var connections = 0
        for (edge in edgesList) {
            uf.union(edge.a, edge.b)
            connections++
            if (connections >= 1000) break
        }

        val sizes = uf.getComponentSizes().sortedDescending()
        return sizes[0] * sizes[1] * sizes[2]
    }

    fun part2(input: List<String>): Long {
        val parse = parse(input)

        val edges = mutableListOf<Edge>()
        for (i in parse.indices) {
            for (j in i + 1 until parse.size) {
                edges.add(Edge(parse[i], parse[j], distance(parse[i], parse[j])))
            }
        }
        edges.sortBy { it.distance }

        val uf = UnionFind(parse)
        var lastEdge: Edge? = null

        for (edge in edges) {
            if (uf.union(edge.a, edge.b)) {
                lastEdge = edge
            }

            if (uf.getComponentSizes().size == 1) break
        }

        return lastEdge!!.a.x.toLong() * lastEdge.b.x.toLong()
    }

    part1(readInput("Day08_test")).println()
    part1(readInput("Day08")).println()
    part2(readInput("Day08")).println()
}