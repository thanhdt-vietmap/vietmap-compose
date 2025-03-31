[![Github Actions](https://github.com/sargunv/maplibre-compose/actions/workflows/ci.yml/badge.svg)](https://github.com/sargunv/maplibre-compose/actions/workflows/ci.yml?query=branch%3Amain)
[![Maven Central Version](https://img.shields.io/maven-central/v/vn.vietmap.maplibre-compose/maplibre-compose)](https://central.sonatype.com/namespace/vn.vietmap.maplibre-compose)
[![License](https://img.shields.io/github/license/sargunv/maplibre-compose)](https://github.com/sargunv/maplibre-compose/blob/main/LICENSE)
[![Documentation](https://img.shields.io/badge/Documentation-blue?logo=MaterialForMkDocs&logoColor=white)](https://sargunv.github.io/maplibre-compose/)
[![API Reference](https://img.shields.io/badge/API_Reference-blue?logo=Kotlin&logoColor=white)](https://sargunv.github.io/maplibre-compose/api/)

# VietMap for Compose Multiplatform

## Introduction

VietMap Compose is a [Compose Multiplatform][compose] wrapper around the
[VietMap][maplibre] SDKs for rendering interactive maps. You can use it to add
maps to your Compose UIs across Android, iOS, Desktop, and Web.

<p float="left">
  <img src="https://github.com/user-attachments/assets/997cf8a4-2841-40c8-b5a1-ef98193b21b2" width="200" alt="iOS Screenshot"/>
  <img src="https://github.com/user-attachments/assets/e450f689-e254-48b7-bd91-3d3042faa290" width="200" alt="Android Screenshot"/>
</p>

## Usage

- [Getting Started](https://sargunv.github.io/maplibre-compose/getting-started/)
- [API Reference](https://sargunv.github.io/maplibre-compose/api/)
- [Demo App](./demo-app)

## Status

A large subset of VietMap's features are already supported, but the full
breadth of the VietMap SDKs is not yet covered. What is already supported may
have bugs. API stability is not yet guaranteed; we're still exploring how best
to express an interactive map API in Compose.

See [the status table][status] for a breakdown of supported features on each
platform. Android and iOS have the most complete support, while Desktop and Web
are still catching up.

[compose]: https://www.jetbrains.com/compose-multiplatform/
[maplibre]: https://maplibre.org/
[status]: https://sargunv.github.io/maplibre-compose/#status
