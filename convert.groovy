// Copyright (c) 2022  Egon Willighagen <egon.willighagen@gmail.com>
//
// GPL v3

@Grab(group='io.github.egonw.bacting', module='managers-rdf', version='0.3.4')
@Grab(group='io.github.egonw.bacting', module='managers-ui', version='0.3.4')

import groovy.json.JsonSlurper

bioclipse = new net.bioclipse.managers.BioclipseManager(".")
rdf = new net.bioclipse.managers.RDFManager(".")

def jsonSlurper = new JsonSlurper()
jsonContent = new File("toxicology.json").text
records = jsonSlurper.parseText(jsonContent).data

println "@prefix dc:    <http://purl.org/dc/elements/1.1/> ."
println "@prefix dct:   <http://purl.org/dc/terms/> ."
println "@prefix foaf:  <http://xmlns.com/foaf/0.1/> ."
println "@prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#> ."
println "@prefix sbd:   <https://www.sbd4nano.eu/rdf/#> ."
println "@prefix xsd:   <http://www.w3.org/2001/XMLSchema#> ."
println "@prefix void:  <http://rdfs.org/ns/void#> ."
println ""
println "<https://fairsharing.org/>"
println " a                    void:DatasetDescription ;"
println " dc:source            <https://fairsharing.org/> ;"
println " dct:title            \"FAIRsharing\"@en ;"
println " dct:license          <http://creativecommons.org/licenses/by-sa/4.0/> ;"
println " foaf:img             <https://api.fairsharing.org/img/fairsharing-attribution.svg> ."
println ""

for (record in records) {
  println "<${record.attributes.url}> a sbd:Database ;"
  println "  dc:source <https://fairsharing.org/> ;"
  if (record.attributes.metadata.name != null)
    println "  rdfs:label \"${record.attributes.metadata.name}\"@en ;"
  if (record.attributes.metadata.description != null)
    println "  dc:description \"${record.attributes.metadata.description}\"@en ;"
  if (record.attributes.metadata.homepage != null)
    println "  foaf:page \"${record.attributes.metadata.homepage}\"@en ;"
  if (record.attributes.licence_links != null && record.attributes.licence_links[0] != null) {
    println "  dct:license <${record.attributes.licence_links[0].licence_url}> ;"
  }
  println ""
}
