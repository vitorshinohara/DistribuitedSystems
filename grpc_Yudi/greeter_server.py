#!/usr/bin/env python
# -*- coding: utf-8 -*-

# Copyright 2015, Google Inc.
# All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are
# met:
#
#     * Redistributions of source code must retain the above copyright
# notice, this list of conditions and the following disclaimer.
#     * Redistributions in binary form must reproduce the above
# copyright notice, this list of conditions and the following disclaimer
# in the documentation and/or other materials provided with the
# distribution.
#     * Neither the name of Google Inc. nor the names of its
# contributors may be used to endorse or promote products derived from
# this software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
# "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
# LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
# A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
# OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
# SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
# LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
# DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
# THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
# (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
# OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

"""The Python implementation of the GRPC helloworld.Greeter server."""

from concurrent import futures
import time

import grpc

import helloworld_pb2
import helloworld_pb2_grpc

_ONE_DAY_IN_SECONDS = 60 * 60 * 24


class Greeter(helloworld_pb2_grpc.GreeterServicer):
	contatos = []
	#def SayHello(self, request, context):
	#  return helloworld_pb2.HelloReply(message='Hello, %s!' % request.name)

	def AdicionarContato(self, request, context):

		x = Contato(str(request.nome),str(request.telefone)) # Cria um contato com os parâmetros passados
		self.contatos.append(x) # Adiciona o contato na lista do servidor
		
		print "Novo contato adicionado. Nome: " + request.nome + " Telefone: " + request.telefone + " \n"

		return helloworld_pb2.Boleano(flag='True') # Retorna uma mensagem de sucesso para o cliente


	def RemoverContato(self, request, context):
		for i in range(len(self.contatos)): # Itera sobre a lista de contatos do servidor
			if (self.contatos[i].telefone == request.telefone && self.contatos[i].nome == request.nome): # Caso o contato atenda os parâmetros 
				self.contatos.pop(i) # Remove da lista
				print "Contato removido"
				return helloworld_pb2.Boleano(flag='True') # Retorna uma mensagem de sucesso

		return helloworld_pb2.Boleano(flag='False') # Retorna uma mensagem de erro
	
	#def ListarContatos(self, request, context):
		

class Contato(): # Objeto contato
	"""Objeto Contato"""
	def __init__(self, nome, telefone):
		#super(Contato(), self).__init__()
		self.nome = nome
		self.telefone = telefone

def serve():
	server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
	helloworld_pb2_grpc.add_GreeterServicer_to_server(Greeter(), server)
	server.add_insecure_port('[::]:50051')
	server.start()

	try:
		while True:
			time.sleep(_ONE_DAY_IN_SECONDS)
	except KeyboardInterrupt:
		server.stop(0)

		

if __name__ == '__main__':
	serve()


