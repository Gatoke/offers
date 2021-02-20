#!/bin/bash

readonly CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)
readonly CURRENT_TAG=$(git tag --points-at HEAD)

function reverse_array() {
  # first argument is the array to reverse
  # second is the output array
  declare -n arr="$1" rev="$2"
  for i in "${arr[@]}"; do
    rev=("$i" "${rev[@]}")
  done
}

function tag_untagged_commits() {
  local untagged_commits
  local lastTag

  # check untagged commits and add them to "untagged_commits " array
  # if commit is tagged - break (don't go deeper)
  while read -r sha1; do
    local tag
    tag=$(git tag --contains "${sha1}")

    # is empty
    if [[ -z "${tag}" ]]; then
      untagged_commits+=("${sha1}")
    else
      lastTag="${tag}"
      break
    fi
  done < <(git rev-list "${CURRENT_BRANCH}")

  local reversed_untagged_commits
  reverse_array untagged_commits reversed_untagged_commits

  # print commits in reverse order - from oldest to newest
  echo "Incrementing from last tag: ${lastTag}"

  local currentTag="${lastTag}"
  for commitHash in "${reversed_untagged_commits[@]}"; do
    local commitMessage
    commitMessage=$(git show -s --format=%B "${commitHash}")

    # extract prefix from commit message - change: | feat: | chore | docs: | fix: | refactor: | style: | test:
    # except ":" character
    local prefix
    prefix="$(echo "${commitMessage}" | grep -o '^[^:]*')"

    # set current newest tag with every iteration
    currentTag=$(./increment_version.sh "${prefix}" "${currentTag}")

    if [[ "${CURRENT_BRANCH}" != "master" ]]; then
      # Add suffix -sha1 (cut by 33 characters so only 7 characters from commit hash are added to the tag
      currentTag+="-${commitHash:: -33}"
    fi

    git tag -a "${currentTag}" -m "${commitMessage}" "${commitHash}"
  done

  echo "Current tag: ${currentTag}"
}

function main() {
  # If current commit is untagged - tag last untagged commits
  if [[ -z "${CURRENT_TAG}" ]]; then
    tag_untagged_commits
  fi
}

main
