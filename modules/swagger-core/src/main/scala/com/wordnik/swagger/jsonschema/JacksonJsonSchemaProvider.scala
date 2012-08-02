package com.wordnik.swagger.jsonschema

import com.wordnik.swagger.core._
import com.wordnik.swagger.core.util.JsonUtil

import com.fasterxml.jackson.databind._
import com.fasterxml.jackson.databind.node._
import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

class JacksonJsonSchemaProvider extends JsonSchemaProvider {
  def read(hostClass: Class[_]): DocumentationObject = {
    val o = new DocumentationObject()
    o.setName(new ApiModelParser(hostClass).readName(hostClass))
    val schema = JsonUtil.getJsonMapper.generateJsonSchema(hostClass)
    val params = new ListBuffer[DocumentationParameter]
    schema.getSchemaNode.findValue("properties") match {
      case n: ObjectNode => params ++= fromObjectNode(n)
      case _ => println("got unknown node")
    }
    o.setFields(params.toList.asJava)
    o
  }

  def fromObjectNode(node: ObjectNode): List[DocumentationParameter] = {
    (for (field <- node.fields().asScala) yield getParam(field.getKey(), field.getValue())).toList
  }

  def getParam(name: String, node: JsonNode): DocumentationParameter = {
//    println("getting param for " + name + ", node: " + node + ", " + node.getClass)

    if (node.getClass() == classOf[ObjectNode]) {
      val on = node.asInstanceOf[ObjectNode]
    }
    val dataType = convertType(node.get("type").asText)
    new DocumentationParameter(
      name,
      null,
      null,
      dataType,
      null,
      null, // allowable values
      false, // required
      false) // allow multiple
  }

  def convertType(in: String): String = {
    in match {
      case "integer" => "int"
      case _ => in
    }
  }
}