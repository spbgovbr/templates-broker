# Templates-broker - API de manutenção e provisão de modelos de documento
O Templates-broker é uma API que provê modelos de documentos para geração de conteúdo dinâmico, podendo ser utilizado para gerar documentos, e-mails, relatórios, páginas em HTML e mais. A ideia é que a estrutura dos modelos não faça parte da aplicação, podendo ser editada a qualquer momento e compartilhada com outras aplicações. O acesso ao modelo é feito através de uma chave de identificação e o preenchimento deverá ser feito pelo cliente.

O broker não faz nenhum tipo de tratamento sobre o conteúdo do modelo e diversas engines podem ser utilizadas, mas inicialmente ele foi desenvolvido para ser utilizado com modelos feitos em **[mustache](https://mustache.github.io/)**. O suporte a diversas linguagens faz com que o mustache seja uma ótima opção.

Os modelos são armazenados em banco e a cada atualização a versão anterior é adicionada ao histórico. Os modelos podem ser alterados e recuperados sem que haja qualquer indisponibilidade das aplicações.

## Requisitos
- Código-fonte do Broker pode ser baixado a partir do link [https://softwarepublico.gov.br/gitlab/ans/templates-broker/tags](https://softwarepublico.gov.br/gitlab/ans/templates-broker/tags)
- [Apache Maven](https://maven.apache.org/) para baixar as dependências e compilar o pacote.
- Servidor [JBoss EAP 7.0.4](https://developers.redhat.com/products/eap/download/) ou [Wildfly 10](http://wildfly.org/downloads/).
- Banco relacional, o Broker foi desenvolvido usando Oracle 12g, mas com pouco esforço pode utilizar o MySQL.
- Conexão com a internet para que o Maven acesse os repositórios hospedeiros das dependências.
- Ferramenta [apiDoc](http://apidocjs.com/) para gerar a documentação da API.

## Procedimentos para instalação
### Configurar as propriedades do datasource no JBoss.
O datasource `jdbc/templates` é declarado no arquivo `templates-ds.xml`, o funcionamento do datasource depende da declaração de algumas **System Properties** no JBoss.

| Chave									| Valor							|
| ------------------------------------- | ----------------------------- |
| br.gov.ans.templates.db.connectionUrl	| String de conexão com o banco	|
| br.gov.ans.templates.db.password 		| Senha do usuário USUARIO_GETD |

Abaixo um exemplo de declaração de propriedades feita no arquivo `standalone.xml`.
```xml
<!-- Geralmente no início do arquivo, após as extensions -->
<system-properties>
	<!-- Outras propriedades ... -->
	<property name="br.gov.ans.templates.db.connectionUrl" value="STRING_CONEXAO_BD_BROKER"/>
	<property name="br.gov.ans.templates.db.password" value="SENHA_USUARIO_USUARIO_GETD"/>
</system-properties>
```

### Criar security-domain no JBoss 
É necessário que haja um security-domain registrado com o nome `ans-ws-auth`, o mesmo pode utilizar um banco de dados[^1] ou o LDAP. É importante destacar que o Broker trabalha com autorização baseada em papéis(RBAC[^2]) e que os usuários precisam ter seus papéis atribuídos.
[^1]: https://access.redhat.com/documentation/en-us/red_hat_jboss_enterprise_application_platform/7.0/html/how_to_configure_identity_management/configuring_a_security_domain_to_use_a_database
[^2]: https://en.wikipedia.org/wiki/Role-based_access_control

### Implantar pacote gerado pelo Maven
Após a realização de todos os passos anteriores, teremos o JBoss pronto para receber o pacote do Templates-Broker. O deploy pode ser feito de diversas maneiras e não é o foco desse manual. 

Para essa etapa é necessário ter o Maven instalado e configurado. Ao realizar o primeiro build devemos desabilitar os testes automatizados, os testes dependem de uma instância ativa e impedirão a geração do pacote.

### Gerar documentação da API
Após a implantação é **fundamental** que a documentação da API seja disponibilizada para os clientes do Broker. A documentação do Broker foi escrita utilizando a ferramenta [apiDoc](http://apidocjs.com/) e os fontes estão no diretório `/src/main/resources/apidoc/`. Será preciso fazer a instalação do apiDoc[^3] e executar o comando abaixo na raiz do projeto.
[^3]: http://apidocjs.com/#install
 
```console
apidoc -f ".*\\.apidoc$" -i src/main/resources/apidoc/ -o <CAMINHO_ONDE_DOCUMENTACAO_SERA_GERADA>
```

A documentação gerada deve ser disponibilizada em um local onde possa ser facilmente acessada pelos clientes.

## Autenticação e Autorização
A autenticação no Templates-Broker é feita através do HTTP Basic e a autorização é baseada em roles/papéis que são atribuídas ao usuário. Os sistemas que utilizarão o broker precisarão de um usuário, esse usuário deve ser previamente cadastrado em uma fonte de dados e receber a role correspondente às suas necessidades. Esses dados serão verificados pelo security-domain `ans-ws-auth` que foi configurado no JBoss.

### Roles/Papéis ###
Existem duas roles de acesso ao Broker, uma com acesso administrativo e outra somente para consulta. As roles precisam ter o nome idêntico ao definido no Broker, caso haja divergência o acesso será negado pelo [JAAS](https://en.wikipedia.org/wiki/Java_Authentication_and_Authorization_Service).

| Role					| Descrição					|
| --------------------- | ------------------------- |
| RO_ADMIN_TEMPLATE		| Perfil de administração	|
| RO_USUARIO_TEMPLATE	| Perfil de consulta		|

## Projetos Relacionados
* [SEI-Broker](https://softwarepublico.gov.br/gitlab/ans/sei-broker/wikis/home)
* [Gerenciador de Templates](https://softwarepublico.gov.br/gitlab/ans/gerenciador-de-templates/wikis/home)