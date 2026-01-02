# Internal vs External Latency

Este projeto demonstra como diferenciar **latência interna de um serviço** e **latência de dependências externas** utilizando métricas em uma aplicação **Spring Boot**.

O objetivo não é criar uma stack completa de observabilidade, mas mostrar como métricas simples, bem posicionadas, já permitem identificar **onde está o gargalo** quando um endpoint fica lento.

---

## Problema

Em sistemas distribuídos, quando um endpoint apresenta alta latência, é comum assumir que o problema está no próprio serviço.  
Na prática, muitas vezes o tempo de resposta é dominado por **dependências externas**.

Medir apenas a latência do endpoint responde *“está lento?”*, mas não responde *“onde está lento?”*.

---

## Abordagem

A aplicação instrumenta dois pontos distintos:

- **Endpoint HTTP do serviço**
- **Chamada à API externa**

Cada ponto possui sua própria métrica de latência, permitindo comparar:

- tempo gasto **dentro do serviço**
- tempo gasto **fora do serviço**

Com isso, é possível identificar se o gargalo está:
- no processamento interno
- ou na dependência externa

---

## Métricas expostas

As métricas são expostas via **Spring Boot Actuator**:

- `service.movies.endpoint.latency`  
  Latência total do endpoint `/movies`

- `external.imdb.movies.latency`  
  Latência da chamada à API externa (IMDB)

Essas métricas podem ser consultadas diretamente pelo Actuator e usadas por ferramentas externas para análise ou automação.

---

## Simulação de latência

Para fins didáticos, o projeto permite simular **latência interna no serviço**, representando cenários como:

- processamento adicional
- contenção de recursos
- lógica síncrona inesperada

Essa simulação permite observar como as métricas se comportam quando o gargalo muda de lugar, tornando o diagnóstico mais claro.

---

## Executando a aplicação

```bash
./mvnw spring-boot:run
```
