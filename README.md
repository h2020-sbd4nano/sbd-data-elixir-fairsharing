# sbd-data-elixir-fairsharing

<img src="https://api.fairsharing.org/img/fairsharing-attribution.svg" alt="FAIRsharing Logo">

This repository converts [FAIRsharing](https://fairsharing.org/) content
to SbD4Nano landscape data. The followin instructions use the
[FAIRSharing API](https://fairsharing.org/API_doc).

# Step 1: login

Copy the `login.json` to `login_private.json` and add your account info. Then run:

```shell
curl --location --request POST 'https://api.fairsharing.org/users/sign_in' \
  --header 'Accept: application/json' \
  --header 'Content-Type: application/json' \
  --data '@login_private.json'
```

# Step 2: Search for toxicology-related records

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

# Step 3: Convert the results to landscape content

```shell
groovy convert.groovy > toxicology.ttl
```
