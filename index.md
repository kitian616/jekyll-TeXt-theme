---
layout: article
key: page-single
---

# Swedish Sensitive Data Archive

## What is it?

The Swedish sensitive data archive is a secure data archive and
sharing platform for sensitive datasets. The archive is designed for
large volumes of research data store and currently has the capacity to
store 1 petabyte of encrypted data.

Data is strongly encrypted with
[crypt4gh](https://www.ga4gh.org/news/crypt4gh-a-secure-method-for-sharing-human-genetic-data/)
(a standard for encrypting genomic and other data types), and stored
within Sweden in highly secure data centers while non-sensitive
metadata can be made available to make datasets discoverable.

## Current status

We are now piloting a standalone instance of the archive for storing
and sharing.

In the future, the archive will also be integrated with the [Federated
EGA](https://ega-archive.org/federated) network to make data even more
discoverable and accessible at the same time as preserving the high
data protection.

## How is the archive being developed?

The Swedish Sensitive Data Archive uses a software stack that has been
developed by NBIS in collaboration with other Nordic ELIXIR nodes
through the [Tryggve](https://neic.no/tryggve2/) project funded by
[NeIC](https://neic.no/) and coordinated with Central EGA through
[ELIXIR](https://elixir-europe.org/communities/human-data).

The entire stack is open source and can be audited.

## How is access to data controlled?

Access to data will be managed through a secure interface where each
data controller can review requests for access to data, and grant or
deny this access.
