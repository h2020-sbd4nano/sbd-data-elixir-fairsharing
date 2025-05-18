// Copyright (c) 2022-2024  Egon Willighagen <egon.willighagen@gmail.com>
//
// GPL v3

@Grab(group='io.github.egonw.bacting', module='managers-rdf', version='1.0.5')
@Grab(group='io.github.egonw.bacting', module='managers-ui', version='1.0.5')

import groovy.json.JsonSlurper

bioclipse = new net.bioclipse.managers.BioclipseManager(".");
rdf = new net.bioclipse.managers.RDFManager(".");

def jsonSlurper = new JsonSlurper()
jsonContent = new File("toxicology.json").text
records = jsonSlurper.parseText(jsonContent).data

store = rdf.createInMemoryStore()

rdf.addPrefix(store, "dc", "http://purl.org/dc/elements/1.1/")
rdf.addPrefix(store, "dct", "http://purl.org/dc/terms/")
rdf.addPrefix(store, "foaf", "http://xmlns.com/foaf/0.1/")
rdf.addPrefix(store, "rdfs", "http://www.w3.org/2000/01/rdf-schema#")
rdf.addPrefix(store, "sbd", "https://www.sbd4nano.eu/rdf/#")
rdf.addPrefix(store, "xsd", "http://www.w3.org/2001/XMLSchema#")
rdf.addPrefix(store, "void", "http://rdfs.org/ns/void#")

propType    = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"
propLabel   = "http://www.w3.org/2000/01/rdf-schema#label"
propSource  = "http://purl.org/dc/elements/1.1/source"
propDesc    = "http://purl.org/dc/elements/1.1/description" 
propLicense = "http://purl.org/dc/terms/license"
propFOAFpage= "http://xmlns.com/foaf/0.1/page"

metaIRI = "https://fairsharing.org/"
rdf.addObjectProperty(store, metaIRI, propType, "http://rdfs.org/ns/void#DatasetDescription")
rdf.addObjectProperty(store, metaIRI, propSource, "https://fairsharing.org/")
rdf.addPropertyInLanguage(store, metaIRI, "http://purl.org/dc/terms/title", "FAIRsharing", "en")
rdf.addObjectProperty(store, metaIRI, "http://xmlns.com/foaf/0.1/img", "https://api.fairsharing.org/img/fairsharing-attribution.svg")
rdf.addObjectProperty(store, metaIRI, propLicense, "http://creativecommons.org/licenses/by-sa/4.0/")

for (record in records) {
  recordIRI = record.attributes.url
  rdf.addObjectProperty(store, recordIRI, propType, "https://www.sbd4nano.eu/rdf/#Database")
  if (record.attributes.metadata.name != null)
    rdf.addPropertyInLanguage(store, recordIRI, propLabel, record.attributes.metadata.name, "en")
  if (record.attributes.metadata.description != null)
    rdf.addPropertyInLanguage(store, recordIRI, propDesc, record.attributes.metadata.description, "en")
  if (record.attributes.metadata.homepage != null)
    rdf.addObjectProperty(store, recordIRI, propFOAFpage, record.attributes.metadata.homepage)
  if (record.attributes.licence_links != null && record.attributes.licence_links[0] != null) {
    rdf.addObjectProperty(store, recordIRI, propLicense, record.attributes.licence_links[0].licence_url)
  }
  rdf.addObjectProperty(store, recordIRI, propSource, "https://fairsharing.org/")
}

println rdf.asTurtle(store)
