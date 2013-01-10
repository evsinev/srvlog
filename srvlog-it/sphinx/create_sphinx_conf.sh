#!/bin/bash

. ../../srvlog-sql/functions.sh

SPHINX_DIR=${SPHINX_DIR:-/usr/lib/sphinx}

cd ../target

SPHINX_DIR=`pwd`

sed 's|@SRVLOG_DB_HOST@|'"$SRVLOG_DB_HOST"'|g'  "../sphinx/sphinx-template.txt" | \
sed 's|@SRVLOG_DB_USERNAME@|'"$SRVLOG_DB_USERNAME"'|g' | \
sed 's|@SRVLOG_DB_PASSWORD@|'"$SRVLOG_DB_PASSWORD"'|g' | \
sed 's|@SRVLOG_DB_DATABASE@|'"$SRVLOG_DB_DATABASE"'|g' | \
sed 's|@SRVLOG_DB_PORT@|'"$SRVLOG_DB_PORT"'|g'  | \
sed 's|@SRVLOG_DB_SOCK@|'"$SRVLOG_DB_SOCK"'|g'  | \
sed 's|@SPHINX_DIR@|'"$SPHINX_DIR"'|g' > "test-sphinx.conf"
