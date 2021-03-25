#!/bin/bash
# Author: Karol Dominiak <gatoke2@gmail.com>
#
# Increments a version depending on "change type". See Semantic Commit Messages: https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716
# $1 - Commit type. Depending on change type the particular section of the version will be incremented.
# $2 - Version to increment. It should have prefix "v" and three sections divided by ".". Example: v3.1.15
#
# Examples:
# Run script with $1=change and $2=v3.1.15          -> v4.0.0
# Run script with $1=feat   and $2=v3.1.15          -> v3.2.0
# Run script with $1=fix    and $2=v3.1.15          -> v3.1.16
# Run script with $1=fix    and $2=v3.1.15-a472dbd3 -> v3.1.16

readonly COMMIT_TYPE=$1
readonly INPUT_VERSION=$2

if [[ -z $1 ]]; then
  echo '1st argument is missing. Available arguments: change | feat | chore | docs | fix | refactor | style | test'
  exit 0
fi

if [[ -z $2 ]]; then
  echo "2nd argument is missing. Pass initial version in format: v(number).(number).(number)"
  echo "Example: v3.1.15"
  exit 0
fi

# divide sectors numbers by whitespaces
# remove "v" at the beginning
# remove commit hash at the end starting with "-" character. e.g. "-1c849af"
divided_by_whitespaces=$(echo "${INPUT_VERSION}" | cut -f1 -d"-" | tr '.' ' ' | cut -c 2-)

# Sectors to array
read -r -a version_sectors <<<"${divided_by_whitespaces}"

# Switch-case of Commit Type = which sector of version to increment
case "${COMMIT_TYPE}" in
change)
  ((version_sectors[0]++))
  version_sectors[1]=0
  version_sectors[2]=0
  ;;
feat)
  ((version_sectors[1]++))
  version_sectors[2]=0
  ;;
chore | docs | fix | refactor | style | test)
  ((version_sectors[2]++))
  ;;
*)
  echo "1st argument is invalid. Available arguments: change | feat | chore | docs | fix | refactor | style | test"
  exit 0
  ;;
esac

# Array back to version of format: v(number).(number).(number)
result="v"
for each in "${version_sectors[@]}"; do
  result+="${each}."
done
result=${result:: -1}

# Return incremented version
echo "${result}"
