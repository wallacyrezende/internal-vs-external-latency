# Automação de Diagnóstico de Latência

Esta pasta contém a stack de automação utilizada para analisar as métricas de latência expostas pelo serviço Spring Boot deste repositório e executar ações com base na **origem da latência** observada.

A automação foi construída especificamente para o serviço `/movies`, que consome uma API externa (IMDB), permitindo identificar se a lentidão percebida pelo cliente está no **processamento interno** ou na **dependência externa**.

---

## Objetivo da automação

O objetivo desta automação é evitar diagnósticos simplistas baseados apenas no tempo de resposta do endpoint.

Para isso, o workflow:

- coleta métricas de latência do endpoint `/movies`
- coleta métricas da chamada à API externa do IMDB
- compara os tempos de forma contextual
- classifica o cenário observado
- executa ações diferentes conforme o diagnóstico

O foco não é alertar que “está lento”, mas indicar **onde está o gargalo**.

---

## Componentes utilizados

A stack de automação é composta por:

- **n8n**  
  Orquestra a execução periódica, a coleta das métricas e a lógica de decisão

- **Evolution API**  
  Utilizada como mecanismo de envio de mensagens para demonstrar ações baseadas no diagnóstico

- **Docker Compose**  
  Responsável por subir a stack localmente de forma simples

---

## Fluxo do workflow

O workflow executa as seguintes etapas:

1. Disparo periódico
2. Execução do endpoint `/movies` para gerar métricas reais
3. Coleta da métrica de latência da API externa (IMDB)
4. Coleta da métrica de latência do endpoint interno
5. Unificação das métricas coletadas
6. Cálculo das latências médias
7. Geração de um diagnóstico
8. Execução da ação correspondente

Toda a lógica de decisão é centralizada em um único ponto, separando claramente:
- cálculo
- diagnóstico
- ação

---

## Diagnóstico produzido

O workflow classifica cada execução em um dos seguintes estados:

- **OK**  
  Latências dentro do comportamento esperado

- **EXTERNAL_LATENCY**  
  A latência da dependência externa é dominante

- **INTERNAL_LATENCY**  
  A latência interna do serviço é significativamente maior que a externa

Essa classificação permite ações mais precisas e evita alertas genéricos.

---

## Thresholds

Os thresholds definidos no workflow têm caráter demonstrativo e foram escolhidos apenas para facilitar a visualização do comportamento do fluxo.

Em um ambiente real, esses valores devem ser ajustados com base em:
- histórico de métricas
- SLOs do serviço
- perfil de carga e tráfego

---

## Executando a automação

Para subir a stack localmente:

```bash
docker compose up
```

Acesse o n8n em `http://localhost:5678` e importe o workflow disponível no arquivo `n8n-workflow.json`.