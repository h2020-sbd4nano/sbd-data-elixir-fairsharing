# sbd-data-elixir-fairsharing
<img src="https://api.fairsharing.org/img/fairsharing-attribution.svg" height="80" alt="FAIRsharing Logo">  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   <img src="img/elixir-tox-logo.png" width="100" alt="ELIXIR Toxicology Community Logo"> 

This repository converts [FAIRsharing](https://fairsharing.org/) content
to SbD4Nano landscape data. The followin instructions use the
[FAIRSharing API](https://fairsharing.org/API_doc). The 
[ELIXIR Toxicology Community](https://elixir-europe.org/communities/toxicology), in collaboration with FAIRsharing, maintains the
[FAIRSharing Toxicology](https://fairsharing.org/3496) collection.

# Update protocol

## Step 1: login

Copy the `login.json` to `login_private.json` and add your account info. Then run:

```shell
curl --location --request POST 'https://api.fairsharing.org/users/sign_in' \
  --header 'Accept: application/json' \
  --header 'Content-Type: application/json' \
  --data '@login_private.json'
```

## Step 2: Search for toxicology-related records

This returns a JSON file with embeded a string for your `jwt`. This you use to replace `$JWT` in the
next command:

```shell
curl --location --request POST 'https://api.fairsharing.org/search/fairsharing_records?q=toxicology' \
  --header 'Accept: application/json' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Bearer $JWT' \
  | jq . > toxicology.json
```

This creates a `toxicology.json` file under the [CC-BY-SA](https://fairsharing.org/licence)
license and can be converted into landscape content.

## Step 3: Convert the results to landscape content

```shell
groovy convert.groovy > toxicology.ttl
```

# References

* Sansone, SA., McQuilton, P., Rocca-Serra, P. et al. FAIRsharing as a community approach to standards, repositories and policies. Nat Biotechnol 37, 358–367 (2019). [https://doi.org/10.1038/s41587-019-0080-8](https://doi.org/10.1038/s41587-019-0080-8)
* Martens, M. et al. ELIXIR and Toxicology: a community in development. F1000Research 10(ELIXIR):1129 (2023). [https://doi.org/10.12688/f1000research.74502.2](https://doi.org/10.12688/f1000research.74502.2)
