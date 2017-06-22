define({ "api": [
  {
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "optional": false,
            "field": "varname1",
            "description": "<p>No type.</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "varname2",
            "description": "<p>With type.</p>"
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "templates-broker/src/main/webapp/api-docs/main.js",
    "group": "C__ANS_ans_templates_templates_broker_src_main_webapp_api_docs_main_js",
    "groupTitle": "C__ANS_ans_templates_templates_broker_src_main_webapp_api_docs_main_js",
    "name": ""
  },
  {
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "optional": false,
            "field": "varname1",
            "description": "<p>No type.</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "varname2",
            "description": "<p>With type.</p>"
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "templates-broker/target/templates-broker-0.1/api-docs/main.js",
    "group": "C__ANS_ans_templates_templates_broker_target_templates_broker_0_1_api_docs_main_js",
    "groupTitle": "C__ANS_ans_templates_templates_broker_target_templates_broker_0_1_api_docs_main_js",
    "name": ""
  },
  {
    "type": "post",
    "url": "/templates/:template/colaboradores",
    "title": "Adicionar colaborador",
    "name": "addColaborador",
    "group": "Colaborador",
    "version": "1.0.0",
    "permission": [
      {
        "name": "RO_ADMIN_TEMPLATE"
      }
    ],
    "description": "<p>Adiciona um colaborador ao template.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "template",
            "description": "<p>Identificador do template</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "\tendpoint: [POST] http://<host>/templates-broker/service/templates/confirmacao-cadastro/colaboradores\n\n\tbody:\n    {\n      \"usuario\": \"andre.guimaraes\",\n      \"editor\": true\n    }",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 201": [
          {
            "group": "Sucesso - 201",
            "type": "header",
            "optional": false,
            "field": "Location",
            "description": "<p>Caminho para o recurso criado.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 201 Created",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "templates-broker/src/main/java/br/gov/ans/templates/rest/TemplateResource.java",
    "groupTitle": "Colaborador"
  },
  {
    "type": "get",
    "url": "/templates/:template/colaboradores/:colaborador",
    "title": "Consultar colaborador",
    "name": "getColaborador",
    "group": "Colaborador",
    "version": "1.0.0",
    "permission": [
      {
        "name": "RO_ADMIN_TEMPLATE, RO_USUARIO_TEMPLATE"
      }
    ],
    "description": "<p>Consulta um colaborador.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "template",
            "description": "<p>Identificador do template</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "colaborador",
            "description": "<p>Identificador do colaborador</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i http://<host>/templates-broker/service/templates/confirmacao-cadastro/colaboradores/andre.guimaraes",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Colaborador",
            "optional": false,
            "field": "colaborador",
            "description": "<p>Objeto representando um colaborador.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "colaborador.usuario",
            "description": "<p>Identificador do colaborador.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Boolean",
            "optional": false,
            "field": "colaborador.editor",
            "description": "<p>Flag que determina se o colaborador tem o poder para editar o template.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"editor\": true,\n  \"usuario\": \"andre.guimaraes\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "templates-broker/src/main/java/br/gov/ans/templates/rest/TemplateResource.java",
    "groupTitle": "Colaborador"
  },
  {
    "type": "get",
    "url": "/templates/:template/colaboradores",
    "title": "Listar colaboradores",
    "name": "getColaboradores",
    "group": "Colaborador",
    "version": "1.0.0",
    "permission": [
      {
        "name": "RO_ADMIN_TEMPLATE, RO_USUARIO_TEMPLATE"
      }
    ],
    "description": "<p>Consulta os colaboradores do template.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "template",
            "description": "<p>Identificador do template</p>"
          }
        ],
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "itens",
            "defaultValue": "20",
            "description": "<p>Quantidade de templates que serão exibidos</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "pag",
            "defaultValue": "1",
            "description": "<p>Número da página</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i http://<host>/templates-broker/service/templates/confirmacao-cadastro/colaboradores",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "List",
            "optional": false,
            "field": "resultado",
            "description": "<p>Lista com os colaboradores do template.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Colaborador",
            "optional": false,
            "field": "resultado.colaborador",
            "description": "<p>Objeto representando um colaborador.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.colaborador.usuario",
            "description": "<p>Identificador do colaborador.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado.colaborador.editor",
            "description": "<p>Flag que determina se o colaborador tem o poder para editar o template.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"editor\": true,\n  \"usuario\": \"andre.guimaraes\",\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "templates-broker/src/main/java/br/gov/ans/templates/rest/TemplateResource.java",
    "groupTitle": "Colaborador"
  },
  {
    "type": "delete",
    "url": "/templates/:template/colaboradores/:colaborador",
    "title": "Remover colaborador",
    "name": "removeColaborador",
    "group": "Colaborador",
    "version": "1.0.0",
    "permission": [
      {
        "name": "RO_ADMIN_TEMPLATE"
      }
    ],
    "description": "<p>Remove colaborador de um template.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "template",
            "description": "<p>Identificador do template</p>"
          },
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "colaborador",
            "description": "<p>Identificador do colaborador</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -X DELETE http://<host>/templates-broker/service/templates/confirmacao-cadastro/colaboradores/andre.guimaraes",
        "type": "json"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "templates-broker/src/main/java/br/gov/ans/templates/rest/TemplateResource.java",
    "groupTitle": "Colaborador"
  },
  {
    "type": "post",
    "url": "/templates",
    "title": "Adicionar template",
    "name": "createTemplate",
    "group": "Template",
    "version": "1.0.0",
    "permission": [
      {
        "name": "RO_ADMIN_TEMPLATE"
      }
    ],
    "description": "<p>Cria um novo template.</p>",
    "parameter": {
      "fields": {
        "Request Body": [
          {
            "group": "Request Body",
            "type": "Template",
            "optional": false,
            "field": "template",
            "description": "<p>Objeto de representação do template.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "corpo",
            "description": "<p>Corpo do template, conteúdo que as aplicações usarão para preencher e exibir os dados.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "descricao",
            "description": "<p>Descrição do template.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "exemplo",
            "description": "<p>Exemplo de request para preenchimento do template.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "nome",
            "description": "<p>Identificador do template.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "responsavel",
            "description": "<p>Analista responsável pelo template.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "restrito",
            "description": "<p>Flag identificando se a atualização deste template � restrita.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "\tendpoint: [PUT] http://<host>/templates-broker/service/templates/confirmacao-cadastro\n\n\tbody:\n    {\n      \"corpo\": \"Prezado {{fulano}}, seu cadastro foi realizado com sucesso.\",\n      \"descricao\": \"Template de confirmação de cadastro.\",\n      \"exemplo\": \"{\"fulano\":\"André Guimarães\"}\",\n      \"nome\": \"confirmacao-cadastro\",\n      \"responsavel\": \"andre.guimaraes\",\n      \"restrito\": true,\n    }",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 201": [
          {
            "group": "Sucesso - 201",
            "type": "header",
            "optional": false,
            "field": "Location",
            "description": "<p>Caminho para o recurso criado.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 201 Created",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "templates-broker/src/main/java/br/gov/ans/templates/rest/TemplateResource.java",
    "groupTitle": "Template"
  },
  {
    "type": "delete",
    "url": "/templates/:template",
    "title": "Excluir template",
    "name": "deleteTemplate",
    "group": "Template",
    "version": "1.0.0",
    "permission": [
      {
        "name": "RO_ADMIN_TEMPLATE"
      }
    ],
    "description": "<p>Exclui determinado template.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "template",
            "description": "<p>Identificador do template</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -X DELETE http://<host>/templates-broker/service/templates/confirmacao-cadastro",
        "type": "json"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "templates-broker/src/main/java/br/gov/ans/templates/rest/TemplateResource.java",
    "groupTitle": "Template"
  },
  {
    "type": "put",
    "url": "/templates/:template",
    "title": "Atualizar template",
    "name": "editTemplate",
    "group": "Template",
    "version": "1.0.0",
    "permission": [
      {
        "name": "RO_ADMIN_TEMPLATE"
      }
    ],
    "description": "<p>Atualiza um template.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "template",
            "description": "<p>Identificador do template</p>"
          }
        ],
        "Request Body": [
          {
            "group": "Request Body",
            "type": "Template",
            "optional": false,
            "field": "template",
            "description": "<p>Objeto de representação do template.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "corpo",
            "description": "<p>Corpo do template, conteúdo que as aplicações usarão para preencher e exibir os dados.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "descricao",
            "description": "<p>Descrição do template.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "exemplo",
            "description": "<p>Exemplo de request para preenchimento do template.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "nome",
            "description": "<p>Identificador do template.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "responsavel",
            "description": "<p>Analista responsável pelo template.</p>"
          },
          {
            "group": "Request Body",
            "type": "String",
            "optional": false,
            "field": "restrito",
            "description": "<p>Flag identificando se a atualização deste template � restrita.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "\tendpoint: [PUT] http://<host>/templates-broker/service/templates/confirmacao-cadastro\n\n\tbody:\n    {\n      \"corpo\": \"Prezado {{fulano}}, seu cadastro foi realizado com sucesso.\",\n      \"descricao\": \"Template de confirmação de cadastro.\",\n      \"exemplo\": \"{\"fulano\":\"André Guimarães\"}\",\n      \"nome\": \"confirmacao-cadastro\",\n      \"responsavel\": \"andre.guimaraes\",\n      \"restrito\": true,\n    }",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "header",
            "optional": false,
            "field": "Location",
            "description": "<p>Caminho para o recurso editado.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "templates-broker/src/main/java/br/gov/ans/templates/rest/TemplateResource.java",
    "groupTitle": "Template"
  },
  {
    "type": "get",
    "url": "/templates/:template/corpo",
    "title": "Recuperar corpo",
    "name": "getCorpoTemplate",
    "group": "Template",
    "version": "1.0.0",
    "permission": [
      {
        "name": "RO_ADMIN_TEMPLATE, RO_USUARIO_TEMPLATE"
      }
    ],
    "description": "<p>Recupera o corpo de um template para utilização.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "template",
            "description": "<p>Identificador do template</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i http://<host>/templates-broker/service/templates/confirmacao-cadastro",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "corpo",
            "description": "<p>String com o corpo do template pronto para utilização.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\nPrezado {{fulano}}, seu cadastro foi realizado com sucesso.",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "templates-broker/src/main/java/br/gov/ans/templates/rest/TemplateResource.java",
    "groupTitle": "Template"
  },
  {
    "type": "get",
    "url": "/templates/:template",
    "title": "Consultar template",
    "name": "getTemplate",
    "group": "Template",
    "version": "1.0.0",
    "permission": [
      {
        "name": "RO_ADMIN_TEMPLATE, RO_USUARIO_TEMPLATE"
      }
    ],
    "description": "<p>Consulta um template.</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "template",
            "description": "<p>Identificador do template</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i http://<host>/templates-broker/service/templates/confirmacao-cadastro",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "Template",
            "optional": false,
            "field": "template",
            "description": "<p>Objeto representando o template.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "template.corpo",
            "description": "<p>Corpo do template, conteúdo que as aplicações usarão para preencher e exibir os dados.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "template.dataCadastro",
            "description": "<p>Data de cadastro do template.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "template.descricao",
            "description": "<p>Descrição do template.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "template.exemplo",
            "description": "<p>Exemplo de request para preenchimento do template.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "template.nome",
            "description": "<p>Identificador do template.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "template.responsavel",
            "description": "<p>Analista responsável pelo template.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Boolean",
            "optional": false,
            "field": "template.restrito",
            "description": "<p>Flag identificando se a atualização deste template � restrita.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"corpo\": \"Prezado {{fulano}}, seu cadastro foi realizado com sucesso.\",\n  \"dataCadastro\": \"2017-03-14T16:57:47.405-03:00\",\n  \"descricao\": \"Template de confirmação de cadastro.\",\n  \"exemplo\": \"{\"fulano\":\"André Guimarães\"}\",\n  \"nome\": \"confirmacao-cadastro\",\n  \"responsavel\": \"andre.guimaraes\",\n  \"restrito\": false,\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "templates-broker/src/main/java/br/gov/ans/templates/rest/TemplateResource.java",
    "groupTitle": "Template"
  },
  {
    "type": "get",
    "url": "/templates/:template/versoes",
    "title": "Listar versões",
    "name": "getVersoes",
    "group": "Template",
    "version": "1.0.0",
    "permission": [
      {
        "name": "RO_ADMIN_TEMPLATE, RO_ADMIN_TEMPLATE"
      }
    ],
    "description": "<p>Lista as versões de um template</p>",
    "parameter": {
      "fields": {
        "Path Parameters": [
          {
            "group": "Path Parameters",
            "type": "String",
            "optional": false,
            "field": "template",
            "description": "<p>Identificador do template</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i http://<host>/templates-broker/service/templates/confirmacao-cadastro/versoes",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "List",
            "optional": false,
            "field": "resultado",
            "description": "<p>Lista com as versões do template.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Versao",
            "optional": false,
            "field": "resultado.versao",
            "description": "<p>Objeto representando uma versão do template.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.versao.corpo",
            "description": "<p>Corpo do template, conteúdo que as aplicações usarão para preencher e exibir os dados.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Date",
            "optional": false,
            "field": "resultado.versao.data",
            "description": "<p>Data da versão.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.versao.descricao",
            "description": "<p>Descrição do template.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.versao.exemplo",
            "description": "<p>Exemplo de request para preenchimento do template.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.versao.responsavel",
            "description": "<p>Identificador do colaborador responsável.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"corpo\": \"Prezado {{fulano}}, seu cadastro foi realizado com sucesso.\",\n  \"data\":\"2017-03-14T16:57:47.405-03:00\",\n  \"descricao\": \"Template de confirmação de cadastro.\",\n  \"exemplo\": \"{\"fulano\":\"André Guimarães\"}\",\n  \"responsavel\": \"andre.guimaraes\",\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "templates-broker/src/main/java/br/gov/ans/templates/rest/TemplateResource.java",
    "groupTitle": "Template"
  },
  {
    "type": "get",
    "url": "/templates",
    "title": "Listar templates",
    "name": "listTemplates",
    "group": "Template",
    "version": "1.0.0",
    "permission": [
      {
        "name": "RO_ADMIN_TEMPLATE, RO_USUARIO_TEMPLATE"
      }
    ],
    "description": "<p>Consulta os templates disponíveis.</p>",
    "parameter": {
      "fields": {
        "Query Parameters": [
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "filtro",
            "description": "<p>Valor utilizado para filtrar os templates.</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "itens",
            "defaultValue": "20",
            "description": "<p>Quantidade de templates que serão exibidos</p>"
          },
          {
            "group": "Query Parameters",
            "type": "String",
            "optional": true,
            "field": "pag",
            "defaultValue": "1",
            "description": "<p>Número da página</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemplo de requisição:\t",
        "content": "curl -i http://<host>/templates-broker/service/templates",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Sucesso - 200": [
          {
            "group": "Sucesso - 200",
            "type": "List",
            "optional": false,
            "field": "resultado",
            "description": "<p>Lista com os templates encontrados.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Template",
            "optional": false,
            "field": "resultado.template",
            "description": "<p>Objeto representando o template.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.template.corpo",
            "description": "<p>Corpo do template, conteúdo que as aplicações usarão para preencher e exibir os dados.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.template.dataCadastro",
            "description": "<p>Data de cadastro do template.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.template.descricao",
            "description": "<p>Descrição do template.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.template.exemplo",
            "description": "<p>Exemplo de request para preenchimento do template.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.template.nome",
            "description": "<p>Identificador do template</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "String",
            "optional": false,
            "field": "resultado.template.responsavel",
            "description": "<p>Analista responsável pelo template.</p>"
          },
          {
            "group": "Sucesso - 200",
            "type": "Boolean",
            "optional": false,
            "field": "resultado.template.restrito",
            "description": "<p>Flag identificando se a atualização deste template � restrita.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"corpo\": \"Prezado {{fulano}}, seu cadastro foi realizado com sucesso.\",\n  \"dataCadastro\": \"2017-03-14T16:57:47.405-03:00\",\n  \"descricao\": \"Template de confirmação de cadastro.\",\n  \"exemplo\": \"{\"fulano\":\"André Guimarães\"}\",\n  \"nome\": \"confirmacao-cadastro\",\n  \"responsavel\": \"andre.guimaraes\",\n  \"restrito\": false\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 500 Internal Server Error\n{\n\t\"error\":\"Mensagem de erro.\"\n\t\"code\":\"código do erro\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "templates-broker/src/main/java/br/gov/ans/templates/rest/TemplateResource.java",
    "groupTitle": "Template"
  }
] });